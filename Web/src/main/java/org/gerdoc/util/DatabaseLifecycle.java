package org.gerdoc.util;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
@WebListener
public class DatabaseLifecycle implements ServletContextListener {
    private org.hibernate.SessionFactory factory;
    public void contextInitialized(ServletContextEvent event) {factory=HibernateUtil.getSessionFactory();}
    public void contextDestroyed(ServletContextEvent event) {if(factory!=null) factory.close();}
}
