package library.management.system.operations;

import org.hibernate.Session;

import java.util.Scanner;

public class Management {

    public static void manage(Session session) {

        boolean flag = true;

        while (flag) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("\nEnter 1 for Register");
            System.out.println("Enter 2 for Login");
            System.out.println("Enter 3 for Exit");

            System.out.print("Enter your choice : ");

            try {

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        Register register = new Register(session);
                        register.register();
                        break;

                    case 2:
                        Login login = new Login(session);
                        login.login();
                        break;

                    case 3:
                        flag = false;
                        break;

                    default:
                        throw new RuntimeException("Invalid choice, Please try again!");

                }
            }  catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

}