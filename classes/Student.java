package library.management.system.classes;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends User{

    @Column(name = "class", nullable = false)
    private String clas;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StudentBook> books = new ArrayList<>();

    public Student() {
    }

    public Student(int id, String name, String clas, String password) {
        this.setId(id);
        this.setName(name);
        this.clas = clas;
        this.password = password;
    }


    public List<StudentBook> getBooks() {
        return books;
    }

    public void setBooks(List<StudentBook> books) {
        this.books = books;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "StudentOpr{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", clas='" + clas + '\'' +
                '}';
    }
}