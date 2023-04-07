package library.management.system.operations;

import library.management.system.classes.Book;
import library.management.system.classes.Librarian;
import library.management.system.classes.Student;
import library.management.system.classes.StudentBook;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LibrarianOpr {

    Session session;
    Librarian librarian;

    public LibrarianOpr(Session session, Librarian librarian) {
        this.session = session;
        this.librarian = librarian;
    }

    public void operation() {

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {

            System.out.println("\nEnter 1 for search book");
            System.out.println("Enter 2 for update stock");
            System.out.println("Enter 3 for add book");
            System.out.println("Enter 4 for remove book");
            System.out.println("Enter 5 for display stock");
            System.out.println("Enter 6 for display users");
            System.out.println("Enter 7 for delete user");
            System.out.println("Enter 8 for back");
            System.out.print("Enter your choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            String title;
            String author;
            try {
                switch (choice) {

                    case 1:
                        System.out.print("\nEnter title of book : ");
                        title = scanner.nextLine();
                        searchBook(title);
                        break;

                    case 2:
                        System.out.print("\nEnter title of book : ");
                        title = scanner.nextLine();
                        System.out.print("Enter author of book : ");
                        author = scanner.nextLine();
                        System.out.print("Enter no of units : ");
                        int noOfUnits = scanner.nextInt();
                        scanner.nextLine();
                        updateStock(title, author, noOfUnits);
                        break;

                    case 3:
                        System.out.print("\nEnter id of book : ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter title of book : ");
                        title = scanner.nextLine();
                        System.out.print("Enter author of book : ");
                        author = scanner.nextLine();
                        System.out.print("Enter no of units : ");
                        int noOfUnit = scanner.nextInt();
                        scanner.nextLine();

                        addBook(id, title, author, noOfUnit);

                        break;

                    case 4:
                        System.out.print("\nEnter title of book : ");
                        title = scanner.nextLine();
                        System.out.print("Enter author of book : ");
                        author = scanner.nextLine();

                        deleteBook(title, author);

                        break;

                    case 5:
                        System.out.println();
                        displayStock();

                        break;
                    case 6:
                        System.out.println();
                        displayUsers();

                        break;
                    case 7:
                        System.out.print("Enter student id : ");
                        int Id = scanner.nextInt();
                        scanner.nextLine();

                        deleteUser(Id);

                        break;
                    case 8:
                        flag = false;
                        break;

                    default:
                        System.out.println("Invalid choice, Please try again!");

                }

            } catch (Exception e) {
            }

        }

    }

    public void updateStock(String title, String author, int noOfUnits) {
        try {

            SQLQuery query = session.createSQLQuery("select * from book where title = '" + title + "' and author = '" + author + "'");
            query.addEntity(Book.class);

            Book book = (Book) query.uniqueResult();

            book.setInStock(book.getInStock() + noOfUnits);

            session.save(book);

            session.beginTransaction().commit();


        } catch (Exception e) {
            System.out.println("No such book found in stock, Please add it.");
        }
    }

    public void addBook(int id, String title, String Author, int noOfUnit) {

        Book book = new Book(id, title, Author, noOfUnit);
        session.save(book);


    }


    public void deleteBook(String title, String author) {
        try {
            SQLQuery query = session.createSQLQuery("select * from book where title like '" + title + "%' and author like '" + author + "%'");
            query.addEntity(Book.class);

            Book book = (Book) query.uniqueResult();

            session.delete(book);

            session.beginTransaction().commit();

        } catch (Exception e) {
            System.out.println("No such book found in stock, Please add it.");
        }
    }

    public void displayStock() {
        SQLQuery query = session.createSQLQuery("select * from book");
        query.addEntity(Book.class);

        List<Book> bookList = query.list();

        for (Book book: bookList) {
            System.out.println(book);
        }

    }

    private void searchBook(String name) {

        Query query = session.createQuery("from Book where title like :name");
        query.setParameter("name", '%' + name + '%');

        List<Book> bookList = (List<Book>) query.list();

        for (Book book : bookList) {
            System.out.println(book.toString());
        }

    }

    @SuppressWarnings("unchecked")
    private void displayUsers() {
        Query query1 = session.createQuery("from Student");
        List<Student> studentList = (List<Student>) query1.list();

        studentList.forEach(student -> System.out.println(student.toString()));

    }

    public void addUser(Student student) {
        session.save(student);

        session.beginTransaction().commit();

    }

    @SuppressWarnings("unchecked")
    private void deleteUser(int id) {
//        show the details that user acquires
        Query query = session.createQuery("from Student where id=:id");
        query.setParameter("id", id);

        Student student = (Student) query.uniqueResult();
        StudentOpr studentOpr = new StudentOpr(session,student);

        studentOpr.checkAccount();

        Query queryBooks = session.createQuery("from StudentBook where student_id =:studentId");
        queryBooks.setParameter("studentId", id);

        List<StudentBook> studentBookList = (List<StudentBook>) queryBooks.list();

        for (StudentBook studentBook: studentBookList) {
            studentOpr.returnBook(studentBook.getBook().getTitle(), studentBook.getBook().getAuthor(), StudentOpr.convertFrom(LocalDate.now()));
        }

        studentOpr.checkAccount();

        session.delete(student);

        session.beginTransaction().commit();

    }

}
