package co.edu.escuelaing;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class UserDAO {

    private final MongoCollection<Document> usersCollection;

    public UserDAO(MongoDatabase database) {
        this.usersCollection = database.getCollection("users");
    }

    public void addUser(String name, int age) {
        Document newUser = new Document("name", name)
                .append("age", age);
        usersCollection.insertOne(newUser);
    }

    public void listUsers() {
        FindIterable<Document> users = usersCollection.find();
        for (Document user : users) {
            System.out.println(user.toJson());
        }
    }

    public void updateUser(String name, int newAge) {
        usersCollection.updateOne(eq("name", name), set("age", newAge));
    }

    public void deleteUser(String name) {
        usersCollection.deleteOne(eq("name", name));
    }

    public void listLastTenUsers() {
        FindIterable<Document> users = usersCollection.find().sort(null).limit(10);
        for (Document user : users) {
            System.out.println(user.toJson());
        }
    }
}