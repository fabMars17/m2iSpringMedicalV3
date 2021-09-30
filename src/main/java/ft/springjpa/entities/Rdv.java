package ft.springjpa.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Rdv {
    private int id;
    private Date date;
    private String type;
    private int duree;
    private String note;
    private Patient patient;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "duree")
    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rdv rdv = (Rdv) o;

        if (id != rdv.id) return false;
        if (duree != rdv.duree) return false;
        if (date != null ? !date.equals(rdv.date) : rdv.date != null) return false;
        if (type != null ? !type.equals(rdv.type) : rdv.type != null) return false;
        if (note != null ? !note.equals(rdv.note) : rdv.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + duree;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
