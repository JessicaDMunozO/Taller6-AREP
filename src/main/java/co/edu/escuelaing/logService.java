package co.edu.escuelaing;

import static spark.Spark.*;

import com.mongodb.client.MongoDatabase;

public class LogService {
    public static void main(String... args) {
        port(getPort());

        MongoDatabase database = MongoUtil.getDB();
        UserDAO userDAO = new UserDAO(database);

        get("logservice", (req, res) -> {
            res.type("application/json");
            userDAO.listLastTenUsers();
            return "{LogIn: \"05/03/2024\"}";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
