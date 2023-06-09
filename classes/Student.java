package library.management.system.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    @Id
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "class", nullable = false)
    private String clas;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
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
        return "Student{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", clas='" + clas + '\'' +
                '}';
    }
}