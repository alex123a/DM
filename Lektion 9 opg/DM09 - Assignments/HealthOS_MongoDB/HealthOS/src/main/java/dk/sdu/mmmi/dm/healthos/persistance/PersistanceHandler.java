package dk.sdu.mmmi.dm.healthos.persistance;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import dk.sdu.mmmi.dm.healthos.domain.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author Oliver Nordestgaard | olnor18
 */

public class PersistanceHandler implements IPersistanceHandler {
    private static final String URL = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE_NAME = "HealthOS";
    private static PersistanceHandler instance;
    private MongoDatabase database;

    private PersistanceHandler() {
        initializeMongoDatabase();
    }

    public static PersistanceHandler getInstance() {
        if (instance == null) {
            instance = new PersistanceHandler();
        }
        return instance;
    }

    private void initializeMongoDatabase() {
        try {
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .codecRegistry(pojoCodecRegistry)
                    .applyConnectionString(new ConnectionString("mongodb://" + URL + ":" + PORT))
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } finally {
            if (database == null) System.exit(-1);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        MongoCollection<Employee> mongoCollection = database.getCollection("employees", Employee.class);
        return mongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public Employee getEmployee(ObjectId id) {
        MongoCollection<Employee> mongoCollection = database.getCollection("employees", Employee.class);
        return mongoCollection.find(Filters.eq("_id", new ObjectId(String.valueOf(id)))).first();
    }

    @Override
    public boolean createEmployee(Employee employee) {
        MongoCollection<Employee> mongoCollection = database.getCollection("employees", Employee.class);
        mongoCollection.insertOne(employee);
        return true;
    }

    /*
    Implement all of the following. Beware that the model classes are as of yet not properly implemented
    */


    @Override
    public List<Patient> getPatients() {
        MongoCollection<Patient> mongoCollection = database.getCollection("patients", Patient.class);
        return mongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public Patient getPatient(int id) {
        MongoCollection<Patient> mongoCollection = database.getCollection("patients", Patient.class);
        return mongoCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public boolean createPatient(Patient patient) {
        //make HealthOS support this action in the presentation layer too.
        MongoCollection<Patient> mongoCollection = database.getCollection("patients", Patient.class);
        mongoCollection.insertOne(patient);
        return true;
    }

    @Override
    public List<Bed> getBeds() {
        MongoCollection<Bed> mongoCollection = database.getCollection("beds", Bed.class);
        return mongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public Bed getBed(int id) {
        MongoCollection<Bed> mongoCollection = database.getCollection("beds", Bed.class);
        return mongoCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public boolean createBed(Bed bed) {
        //make HealthOS support this action in the presentation layer too.
        MongoCollection<Bed> mongoCollection = database.getCollection("beds", Bed.class);
        mongoCollection.insertOne(bed);
        return true;
    }

    @Override
    public List<Admission> getAdmissions() {
        MongoCollection<Admission> mongoCollection = database.getCollection("admissions", Admission.class);
        return mongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public Admission getAdmission(int id) {
        MongoCollection<Admission> mongoCollection = database.getCollection("admissions", Admission.class);
        return mongoCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public boolean createAdmission(Admission admission) {
        //make HealthOS support this action in the presentation layer too.
        MongoCollection<Admission> mongoCollection = database.getCollection("admissions", Admission.class);
        mongoCollection.insertOne(admission);
        return true;
    }

    @Override
    public boolean deleteAdmission(int id) {
        MongoCollection<Admission> mongoCollection = database.getCollection("admissions", Admission.class);
        mongoCollection.deleteOne(Filters.eq("_id", id));
        return true;
    }

}
