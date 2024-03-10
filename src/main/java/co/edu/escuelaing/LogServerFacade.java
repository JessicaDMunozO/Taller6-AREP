package co.edu.escuelaing;

import static spark.Spark.*;

public class LogServerFacade {
    private static final String[] LOG_SERVICE_URL = { "http://logservice1:35000/logservice",
            "http://logservice2:35000/logservice", "http://logservice3:35000/logservice" };

    public static void main(String[] args) {
        port(getPort());
        RemoteLogServiceInvoker invoker = new RemoteLogServiceInvoker(LOG_SERVICE_URL);

        staticFiles.location("/public");

        get("logservicefacade", (req, res) -> {
            res.type("application/json");
            String param = req.queryParams("param");
            return invoker.invoke(param);
        });

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 46000;
    }
}