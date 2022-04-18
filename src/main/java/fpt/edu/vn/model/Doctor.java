package fpt.edu.vn.model;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "id_doctor")
public class Doctor extends User {

    public Doctor() {
    }

}
