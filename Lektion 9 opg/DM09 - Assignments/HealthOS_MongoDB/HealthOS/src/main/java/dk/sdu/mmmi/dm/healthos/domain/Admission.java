package dk.sdu.mmmi.dm.healthos.domain;

import com.google.gson.Gson;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * @author Oliver Nordestgaard | olnor18
 */

public class Admission {
    @BsonProperty("_id")
    private int id;
    private int patient_id;
    private int room_id;
    private int bed_id;
    private int assigned_employee_id;

    public Admission() {

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getBed_id() {
        return bed_id;
    }

    public void setBed_id(int bed_id) {
        this.bed_id = bed_id;
    }

    public int getAssigned_employee_id() {
        return assigned_employee_id;
    }

    public void setAssigned_employee_id(int assigned_employee_id) {
        this.assigned_employee_id = assigned_employee_id;
    }
}
