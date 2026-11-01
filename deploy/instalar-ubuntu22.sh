#!/usr/bin/env bash
# Ejecutar en la VM nueva: sudo bash deploy/instalar-ubuntu22.sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
[[ $EUID = 0 ]] || { echo 'Ejecuta el instalador con sudo.'; exit 1; }
source /etc/os-release
[[ "$ID" = ubuntu && "$VERSION_ID" = 22.04 ]] || { echo 'Se requiere Ubuntu 22.04.'; exit 1; }
[[ -f "$ROOT/Escuela-SpringBoot/target/escuela-api.jar" && -f "$ROOT/Web/target/Web.war" ]] || { echo 'Compila ambos proyectos antes de instalar.'; exit 1; }
export DEBIAN_FRONTEND=noninteractive
apt-get update
apt-get install -y apache2 mysql-server tomcat9 openjdk-17-jdk maven git curl python3
systemctl enable --now mysql

# Se conserva el acceso local administrativo por socket de MySQL.
# La aplicación utiliza un usuario independiente con permisos limitados a ESCUELA.
if [[ ! -f /etc/escuela.env ]]; then
    EXISTS=$(mysql -Nse "SELECT COUNT(*) FROM information_schema.SCHEMATA WHERE SCHEMA_NAME='ESCUELA'")
    [[ "$EXISTS" = 0 ]] || { echo 'ESCUELA ya existe: revisa o respalda esa base antes de instalar.'; exit 1; }
    DB_SECRET=$(openssl rand -hex 24)
    mysql < "$ROOT/sql/ESCUELA.sql"
    mysql <<SQL
CREATE USER 'escuela_app'@'127.0.0.1' IDENTIFIED BY '${DB_SECRET}';
GRANT SELECT, INSERT, UPDATE, DELETE ON ESCUELA.* TO 'escuela_app'@'127.0.0.1';
SQL
    umask 077
    cat > /etc/escuela.env <<ENV
DB_URL=jdbc:mysql://127.0.0.1:3306/ESCUELA?connectionTimeZone=UTC
DB_USER=escuela_app
DB_PASSWORD=${DB_SECRET}
API_BIND=127.0.0.1
API_PORT=9080
ENV
    unset DB_SECRET
fi

id escuela-api >/dev/null 2>&1 || useradd --system --home /opt/escuela --shell /usr/sbin/nologin escuela-api
install -d -o escuela-api -g escuela-api /opt/escuela
install -m 644 "$ROOT/Escuela-SpringBoot/target/escuela-api.jar" /opt/escuela/escuela-api.jar
install -m 644 "$ROOT/deploy/escuela-api.service" /etc/systemd/system/escuela-api.service
install -o tomcat -g tomcat -m 644 "$ROOT/Web/target/Web.war" /var/lib/tomcat9/webapps/Web.war

install -d /etc/systemd/system/tomcat9.service.d
cat > /etc/systemd/system/tomcat9.service.d/escuela.conf <<'UNIT'
[Service]
EnvironmentFile=/etc/escuela.env
Environment="JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
Environment="JRE_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
UNIT
python3 - <<'PY'
from pathlib import Path
import re
file=Path('/etc/tomcat9/server.xml')
text=file.read_text()
text=re.sub(r'(<Connector\s+)(?=port="8080")',r'\1address="127.0.0.1" ',text)
file.write_text(text)
PY
a2enmod proxy proxy_http headers
install -m 644 "$ROOT/deploy/escuela.conf" /etc/apache2/sites-available/escuela.conf
a2ensite escuela.conf
a2dissite 000-default.conf
apache2ctl configtest
systemctl daemon-reload
systemctl enable escuela-api tomcat9 apache2
systemctl restart escuela-api tomcat9 apache2

for attempt in $(seq 1 60); do
    if curl -fsS http://127.0.0.1:9080/api/health >/dev/null && curl -fsS http://127.0.0.1:8080/Web/ >/dev/null; then
        systemctl is-active apache2 mysql tomcat9 escuela-api
        echo 'Instalación terminada. Comprueba /Web/ y /swagger-ui.html desde el navegador.'
        exit 0
    fi
    sleep 2
done
echo 'La comprobación no terminó. Revisa los servicios con systemctl y journalctl.'
exit 1
