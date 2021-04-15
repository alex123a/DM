package dk.sdu.mmmi.dm.healthos.domain;

import com.google.gson.Gson;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * @author Oliver Nordestgaard | olnor18
 */

public class Patient {
    @BsonProperty("_id")
    private int id;
    private String name;
    private String phone;
    private String cpr;

    public Patient() {

    }

    public Patient(int id, String name, String phone, String cpr) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cpr = cpr;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }
}
