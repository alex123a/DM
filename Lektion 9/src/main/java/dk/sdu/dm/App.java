package dk.sdu.dm;

import org.bson.Document;

import java.util.Arrays;

public class App {
    public static void main( String[] args ) {
        Document johndoe = new Document("_id", "1")
                .append("name", "John Doe")
                .append("cpr", "111111-1111")
                .append("departments", Arrays.asList("Odense", "Aarhus", "Vejle"));
        System.out.println(johndoe.toJson());
    }
}
