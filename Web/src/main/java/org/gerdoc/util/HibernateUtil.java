package org.gerdoc.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.gerdoc.model.*;
public final class HibernateUtil {
    private HibernateUtil() { }
    private static class Holder {private static final SessionFactory FACTORY=create();}
    public static SessionFactory getSessionFactory() {return Holder.FACTORY;}
    private static String env(String key,String fallback) {String value=System.getenv(key);return value==null?fallback:value;}
    private static SessionFactory create() {
        String password=System.getenv("DB_PASSWORD");
        if(password==null) throw new IllegalStateException("Falta configurar DB_PASSWORD en el servidor");
        return new Configuration()
            .setProperty("hibernate.connection.driver_class",env("DB_DRIVER",env("DB_URL","").startsWith("jdbc:h2:")?"org.h2.Driver":"com.mysql.cj.jdbc.Driver"))
            .setProperty("hibernate.connection.url",env("DB_URL","jdbc:mysql://127.0.0.1:3306/ESCUELA?connectionTimeZone=UTC"))
            .setProperty("hibernate.connection.username",env("DB_USER","escuela_app"))
            .setProperty("hibernate.connection.password",password)
            .setProperty("hibernate.dialect",env("DB_DIALECT","org.hibernate.dialect.MySQL8Dialect"))
            .setProperty("hibernate.hbm2ddl.auto","validate")
            .setProperty("hibernate.connection.pool_size","8")
            .setProperty("hibernate.show_sql","false")
            .addAnnotatedClass(Alumno.class).addAnnotatedClass(Materia.class).addAnnotatedClass(Profesor.class)
            .addAnnotatedClass(AlumnoMateria.class).addAnnotatedClass(ProfesorMateria.class).buildSessionFactory();
    }
}
