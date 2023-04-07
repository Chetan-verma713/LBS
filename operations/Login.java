package library.management.system.operations;

import library.management.system.classes.Librarian;
import library.management.system.classes.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.util.Objects;
import java.util.Scanner;

public class Login {
    
    Session session;

    public Login(Session session) {
        this.session = session;
    }
    
    public void login() {

        boolean flag = true;
        while (flag) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("\nEnter 1 for Librarian");
            System.out.println("Enter 2 for Student");
            System.out.println("Enter 3 for back");
            System.out.print("Enter your user-type : ");

            int userType = scanner.nextInt();

            try {
                switch (userType) {
                    case 1:
                        loginLibrarian();
                        break;
                    case 2:
                        loginStudent();
                        break;
                    case 3:
                        flag = false;
                        break;
                    default:
                        throw new RuntimeException("Invalid input!");
                }
            } catch (Exception e) {
            }

        }

    }

    private void loginStudent() {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("\nEnter your id : ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter your password : ");
            String password = scanner.nextLine();

            Student student = getStudentAccount(id, password);

            if (student.getName() != null) {
                System.out.println("Student has been login successfully. ");
                StudentOpr studentOpr = new StudentOpr(session, student);
                studentOpr.operation();
            }
            else {
                System.out.println("Invalid id or password");
            }

        } catch (NullPointerException e) {
            System.out.println("Student not found! ");
        } catch (Exception e) {
        }

        session.getTransaction().commit();
    }

    private void loginLibrarian() {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("\nEnter your id : ");
            int id = scanner.nextInt();
            scanner.nextLine();


            System.out.print("Enter your password : ");
            String password = scanner.nextLine();

            Librarian librarian = getLibrarianAccount(id, password);

            if (librarian.getName() != null) {
                System.out.println("Librarian has been login successfully. ");
                LibrarianOpr librarianOpr = new LibrarianOpr(session, librarian);
                librarianOpr.operation();
            }
            else {
                System.out.println("Invalid id or password");
            }

        } catch (NullPointerException e) {
            System.out.println("Librarian not found! ");
        } catch (Exception e) {
        }

        session.getTransaction().commit();

    }

    private Librarian getLibrarianAccount(int id, String pass) {

        Query query = session.createQuery("from Librarian where id =:id");

        query.setParameter("id", id);

        Librarian librarian = (Librarian) query.uniqueResult();

        if (Objects.equals(pass, librarian.getPassword())) {
            System.out.println("welcome back : " + librarian.getName());
            return librarian;
        }

        return null;

    }

    private Student getStudentAccount(int id, String pass) {

        Query query = session.createQuery("from Student where id =:id");

        query.setParameter("id", id);

        Student student = (Student) query.uniqueResult();

        if (Objects.equals(pass, student.getPassword())) {
            System.out.println("welcome back : " + student.getName());
            return student;
        }

        return null;

    }
}
