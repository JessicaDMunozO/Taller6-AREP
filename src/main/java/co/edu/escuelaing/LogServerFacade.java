package co.edu.escuelaing;

import static spark.Spark.*;

public class LogServerFacade {
    private static final String LOG_SERVICE_URL = "http://localhost:5000/logservice";
    public static void main(String[] args) {
        RemoteLogServiceInvoker invoker = new RemoteLogServiceInvoker(new String[] (LOG_SERVICE_URL));

        staticFiles.location("/public");

        get("logservicefacade", (req, res) -> {
            res.type("application/json");
            return invoker.invoke(args);
        });
    }
}