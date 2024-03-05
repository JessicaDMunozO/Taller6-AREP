package co.edu.escuelaing;

import static spark.Spark.*;

public class logService {
    public static void main(String... args) {
        port(getPort());
        staticFiles.location("/public");

        get("hello", (req, res) -> "Hello Docker!");
        get("string/:val", (req, res) -> {
            Double sin = Math.sin(Math.toRadians(Double.valueOf(req.params(":val"))));
            return "The result is: " + sin;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
