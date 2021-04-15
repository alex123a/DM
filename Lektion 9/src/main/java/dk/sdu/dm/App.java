package dk.sdu.dm;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main( String[] args ) {
        // To remove all the standard red spam the mongoDB driver makes in terminal.
        Logger.getLogger("").setLevel(Level.WARNING);

        // Manual mongodb example
        String connectionString = "mongodb://localhost:27017/";
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MyWebsite");
        MongoCollection usersCollection = mongoDatabase.getCollection("Users");

        // Creating json object
        Document johndoe = new Document("_id", "1")
                .append("name", "John Doe")
                .append("cpr", "111111-1111")
                .append("departments", Arrays.asList("Odense", "Aarhus", "Vejle"));

        // Printing json object
        System.out.println(johndoe.toJson());

        // Inserting document
        usersCollection.insertOne(johndoe);

        // Query for user
        Document result = (Document) usersCollection.find(Filters.eq("name", "John Doe")).first();
        System.out.println(result.toJson());

        // Automatic POJO example
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString)).codecRegistry(codecRegistry).build();
        MongoClient pojoClient = MongoClients.create(mongoClientSettings);

        MongoDatabase pojoDatabase = pojoClient.getDatabase("MyWebsite");
        MongoCollection pojoUsersCollection = pojoDatabase.getCollection("Users");

    }
}
