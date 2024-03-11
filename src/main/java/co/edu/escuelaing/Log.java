package co.edu.escuelaing;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Log {

    private final MongoCollection<Document> logsCollection;

    public Log(MongoDatabase database) {
        this.logsCollection = database.getCollection("logs");
    }

    /**
     * Add a new registry to the collection
     * 
     * @param log  a string for the registry
     * @param date the date of the log
     */
    public void addLog(String log, Date date) {
        Document newLog = new Document("log", log)
                .append("date", date);
        logsCollection.insertOne(newLog);
    }

    /**
     * Retrieves the last ten logs from the collection
     * 
     * @return a JSONArray with the last ten logs
     */
    public JSONArray listLastTenLogs() {
        ArrayList<Document> lastLogs = logsCollection.find().sort(Sorts.descending("date")).limit(10)
                .into(new ArrayList<>());
        JSONArray jsonArray = new JSONArray();
        for (Document doc : lastLogs) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("log", doc.getString("log"));
            jsonObject.put("date", ((Date) doc.get("date")).toInstant().toString());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}