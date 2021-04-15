package dk.sdu.mmmi.dm.healthos.domain;

/**
 *
 * @author Oliver Nordestgaard | olnor18
 */
public class Admission {
    private int id;
    private int patient_id;
    private int room_id;
    private int bed_ind;
    private int assigned_employee_id;

    public Admission(int id, int patient_id, int room_id, int bed_ind, int assigned_employee_id) {
        this.id = id;
        this.patient_id = patient_id;
        this.room_id = room_id;
        this.bed_ind = bed_ind;
        this.assigned_employee_id = assigned_employee_id;
    }

    public int getId() {
        return id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getBed_ind() {
        return bed_ind;
    }

    public int getAssigned_employee_id() {
        return assigned_employee_id;
    }
}
