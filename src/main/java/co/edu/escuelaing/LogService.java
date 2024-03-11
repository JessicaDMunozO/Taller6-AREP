package co.edu.escuelaing;

import static spark.Spark.*;

import java.util.Date;

import org.json.JSONArray;

import com.mongodb.client.MongoDatabase;

public class LogService {
    public static void main(String... args) {
        port(getPort());

        MongoDatabase database = MongoUtil.getDB();
        Log log = new Log(database);

        get("logservice", (req, res) -> {
            res.type("application/json");

            String param = req.queryParams("param");
            Date date = new Date();
            log.addLog(param, date);

            JSONArray jsonArray = log.listLastTenLogs();
            return jsonArray.toString();
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }
}
