package library.management.system.classes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentBookId implements Serializable {

    @Column(name = "studentList_id")
    private int studentListId;
    @Column(name = "bookList_id")
    private int bookListId;

    public StudentBookId() {
    }

    public StudentBookId(int studentListId, int bookListId) {
        this.studentListId = studentListId;
        this.bookListId = bookListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentBookId that = (StudentBookId) o;
        return studentListId == that.studentListId && bookListId == that.bookListId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentListId, bookListId);
    }
}
