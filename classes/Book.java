package library.management.system.classes;


import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Book {
    @Id
    private int id;
    @NaturalId
    private String title;
    private String author;
    @Column(name = "in_stock")
    private int inStock;
    @Transient
    private LocalDate dateIssue;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StudentBook> students = new ArrayList<>();


    public Book() {
    }

    public Book(String title, int inStock) {
        this.title = title;
        this.inStock = inStock;
    }

    public Book(int id, String title, String author, int inStock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.inStock = inStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public List<StudentBook> getStudents() {
        return students;
    }

    public void setStudents(List<StudentBook> students) {
        this.students = students;
    }

    public LocalDate getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(LocalDate dateIssue) {
        this.dateIssue = dateIssue;
    }



    public void showDueDate(Session session, Student student) {
        try {
            SQLQuery query = session.createSQLQuery("select issued_date from staff_book where book_id = " + getId() + " and staff_id = " + student.getId());

            Timestamp issueDateTime = (Timestamp) query.uniqueResult();

            LocalDate localDate = issueDateTime.toLocalDateTime().toLocalDate();

            LocalDate dueDate = localDate.plusDays(10);

            System.out.println(getTitle() + "'s due-date is " + dueDate);

        } catch (NullPointerException e) {
            System.err.println("\nNo Book issued named " + title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}