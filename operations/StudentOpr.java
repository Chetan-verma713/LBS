package library.management.system.operations;

import library.management.system.classes.Book;
import library.management.system.classes.Student;
import library.management.system.classes.StudentBook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.hibernate.Query;
import org.hibernate.Transaction;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StudentOpr {

    Session session;
    Student student;
    public StudentOpr(Session session, Student student) {
        this.session = session;
        this.student = student;
    }

    public void operation() {

        session.beginTransaction();

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {

            System.out.println("\nEnter 1 for search book");
            System.out.println("Enter 2 for borrow book");
            System.out.println("Enter 3 for return book");
            System.out.println("Enter 4 for check account");
            System.out.println("Enter 5 for back");
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

                        borrowBook(title, author, convertFrom(LocalDate.now()));
                        break;

                    case 3:
                        System.out.print("\nEnter title of book : ");
                        title = scanner.nextLine();
                        System.out.print("Enter author of book : ");
                        author = scanner.nextLine();

                        returnBook(title, author, convertFrom(LocalDate.now()));

                        break;

                    case 4:
                        checkAccount();
                        break;

                    case 5:
                        flag = false;
                        break;

                    default:
                        System.out.println("Invalid choice, Please try again!");

                }
            } catch (Exception e) {
            }

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

    public void borrowBook(String title, String author, Date issuedDate) {

        Query bookQuery = session.createQuery("from Book where title = :title and author = :author");

        bookQuery.setParameter("title", title);
        bookQuery.setParameter("author", author);

        Book book = (Book) bookQuery.uniqueResult();

        StudentBook studentBook = new StudentBook(student, book, issuedDate);

        student.getBooks().add(studentBook);

        book.getStudents().add(studentBook);
        book.setInStock(book.getInStock()-1);

        studentBook.setReturnDate(null);

        session.getTransaction().commit();

    }

    public void returnBook(String title, String author, Date returnBook) {

        Query bookQuery = session.createQuery("from Book where title = :title and author = :author");

        bookQuery.setParameter("title", title);
        bookQuery.setParameter("author", author);

        Book book = (Book) bookQuery.uniqueResult();

        for (StudentBook studentBook : student.getBooks()) {
            if (studentBook.getStudent().equals(student) &&
                    studentBook.getBook().equals(book)) {
                book.setInStock(book.getInStock() + 1);
                studentBook.setReturnDate(returnBook);
            }
        }

        session.beginTransaction().commit();

    }

    @SuppressWarnings("unchecked")
    public void checkAccount() {

        SQLQuery query = session.createSQLQuery("select sb.student_id, sb.book_id, sb.borrow_date, sb.return_date from student s inner join student_book sb on s.id = sb.student_id where s.id = " + student.getId());
        query.addEntity(StudentBook.class);
        List<StudentBook> studentBooks = query.list();

        calculateFine(studentBooks);

    }

    private void calculateFine(List<StudentBook> books) {

        int borrowedBooks = books.size();

        System.out.println("\nNo. of borrowed books : " + borrowedBooks);

        int reservedBooks = 0;

        for (StudentBook book: books) {
            if (((book).getReturnDate() == null)) {
                reservedBooks++;
            }
        }

        System.out.println("No. of reserved books : " + reservedBooks);

        int returnedBooks = borrowedBooks-reservedBooks;
        System.out.println("No. of returned books : " + returnedBooks);


        double totalFine = 0;
        double perDayFine = 5;

        for (StudentBook book : books) {

            double perBookFine = 0;

            Date borrowedDate = (book).getBorrowDate();
            Date returnedDate = (book).getReturnDate() == null
                    ? convertFrom(LocalDateTime.now().toLocalDate())
                    : (book).getReturnDate();

            Period diff = Period.between(getLocalDateFromDate(borrowedDate), getLocalDateFromDate(returnedDate));
            long totalDays = Math.abs(diff.getDays());

            if (totalDays > 10) {
                perBookFine = (totalDays-10) * perDayFine;
            }

            totalFine += perBookFine;

        }

        System.out.println("Total fine = Rs" + totalFine);
    }

    private LocalDate getLocalDateFromDate(Date date){
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    public static java.sql.Date convertFrom(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }
}
