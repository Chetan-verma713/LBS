package library.management.system;

import library.management.system.classes.*;
import library.management.system.operations.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {

        System.out.println("Project started!");

        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(StudentBook.class)
                .addAnnotatedClass(Librarian.class)
                .addAnnotatedClass(Admin.class);


        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        Session session = sessionFactory.openSession();

        Management.manage(session);

        System.out.println("\nconnection closed successful");
        session.close();

    }

}