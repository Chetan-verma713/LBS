package library.management.system.classes;


import javax.persistence.*;

@Entity
@Table(name = "librarian")
public class Librarian{
    @Id
    private int id;
    private String name;
    private String password;
    public Librarian() {
    }

    public Librarian(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
