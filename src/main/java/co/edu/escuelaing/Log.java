package co.edu.escuelaing;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Log {

    private final MongoCollection<Document> logsCollection;

    public Log(MongoDatabase database) {
        this.logsCollection = database.getCollection("logs");
    }

    public void addLog(String log, String date) {
        Document newLog = new Document("log", log)
                .append("date", date);
        logsCollection.insertOne(newLog);
    }

    public JSONArray listLastTenLogs() {
        ArrayList<Document> lastLogs = logsCollection.find().sort(Sorts.descending("date")).limit(10)
                .into(new ArrayList<>());
        JSONArray jsonArray = new JSONArray();
        for (Document doc : lastLogs) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("log", doc.getString("log"));
            jsonObject.put("date", doc.getString("date"));
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}