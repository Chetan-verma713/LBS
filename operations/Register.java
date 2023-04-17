package library.management.system.operations;

import library.management.system.classes.Librarian;
import library.management.system.classes.Student;

import org.hibernate.Session;

import java.util.Scanner;

public class Register {

    Session session;

    public Register(Session session) {
        this.session = session;
    }

    public void register() {

        boolean flag = true;

        while (flag) {

            session.getTransaction().begin();

            Scanner scanner = new Scanner(System.in);

            System.out.println("\nEnter 1 for Librarian");
            System.out.println("Enter 2 for Student");
            System.out.println("Enter 3 for back");
            System.out.print("Enter your user-type : ");

            int userType = scanner.nextInt();

            switch (userType) {
                case 1:
                    registerLibrarian();
                    break;

                case 2:
                    registerStudent();
                    break;

                case 3:
                    flag = false;
                    break;

                default:
                    System.out.println("Invalid input!");
            }

            session.getTransaction().commit();

        }

    }

    private void registerStudent() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter your id : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your name : ");
        String name = scanner.nextLine();

        System.out.print("Enter your class : ");
        String claz = scanner.nextLine();

        System.out.print("Enter your password :");
        String pass = scanner.nextLine();

        Student student = new Student(id, name, claz, pass);

        session.save(student);

        System.out.println("Student has been registered successfully. ");

    }

    private void registerLibrarian() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter your id : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your name : ");
        String name = scanner.nextLine();

        System.out.print("Enter your password : ");
        String password = scanner.nextLine();

        Librarian librarian = new Librarian();
        librarian.setId(id);
        librarian.setName(name);
        librarian.setPassword(password);

        session.save(librarian);

        System.out.println("Librarian has been registered successfully. ");

    }


}
