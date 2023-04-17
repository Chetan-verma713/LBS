package library.management.system.classes;

import javax.persistence.*;


@MappedSuperclass
public class User<T> {
    @Id
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
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

//    abstract void getBookInfo();

//    @SuppressWarnings("unchecked")

}
