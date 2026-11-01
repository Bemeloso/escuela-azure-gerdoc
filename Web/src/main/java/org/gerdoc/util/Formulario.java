package org.gerdoc.util;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
public final class Formulario {
    private Formulario() { }
    public static String texto(HttpServletRequest r,String key) {
        String s=r.getParameter(key);if(s==null || s.trim().isEmpty() || s.trim().length()>100) throw new IllegalArgumentException("Completa "+key+" con un máximo de 100 caracteres");return s.trim();
    }
    public static Long id(String value) {try {long id=Long.parseLong(value);if(id<=0) throw new NumberFormatException();return id;}catch(Exception ex){throw new IllegalArgumentException("Selecciona un registro válido");}}
    public static int entero(HttpServletRequest r,String key,int max) {try {int n=Integer.parseInt(r.getParameter(key));if(n<1 || n>max) throw new NumberFormatException();return n;}catch(Exception ex){throw new IllegalArgumentException(key+" debe ser un entero entre 1 y "+max);}}
    public static BigDecimal nota(HttpServletRequest r,String key,boolean optional) {
        String s=r.getParameter(key);if(optional && (s==null || s.isBlank())) return null;
        try {BigDecimal n=new BigDecimal(s);if(n.scale()>2 || n.signum()<0 || n.compareTo(BigDecimal.TEN)>0) throw new NumberFormatException();return n;}catch(Exception ex){throw new IllegalArgumentException(key+" debe estar entre 0 y 10, con máximo dos decimales");}
    }
}
