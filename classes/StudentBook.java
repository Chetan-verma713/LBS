package library.management.system.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "student_book")
public class StudentBook {
    @EmbeddedId
    private StudentBookId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentListId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookListId")
    private Book book;
    @Column(name = "borrow_date")
    private Date borrowDate = new Date();
    @Column(name = "return_date")
    private Date returnDate = new Date();
    public StudentBook() {
    }

    public StudentBook(Student student, Book book, Date borrowDate) {
        this.id = new StudentBookId(student.getId(), book.getId());
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentBook that = (StudentBook) o;
        return Objects.equals(student, that.student) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, book);
    }
}
