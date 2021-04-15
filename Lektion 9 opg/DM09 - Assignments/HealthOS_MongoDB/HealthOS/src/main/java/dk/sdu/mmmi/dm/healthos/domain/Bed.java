package dk.sdu.mmmi.dm.healthos.domain;

import com.google.gson.Gson;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * @author Oliver Nordestgaard | olnor18
 */

public class Bed {
    @BsonProperty("_id")
    private int id;
    private String bed_number;

    public Bed() {

    }

    public Bed(int id, String bed_number) {
        this.id = id;
        this.bed_number = bed_number;
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

    public String getBed_number() {
        return bed_number;
    }

    public void setBed_number(String bed_number) {
        this.bed_number = bed_number;
    }
}
