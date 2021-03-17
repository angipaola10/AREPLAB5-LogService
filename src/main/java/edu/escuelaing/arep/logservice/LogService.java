package edu.escuelaing.arep.logservice;

import com.google.gson.Gson;
import edu.escuelaing.arep.logservice.model.Message;
import edu.escuelaing.arep.logservice.service.MessageServer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;

/**
 * Main class of Log sevice
 */
public class LogService {

    /**
     * The entry point of Log Service application
     *
     * @param args the args
     */
    public static void main(String... args){
        port(getPort());
        MessageServer ms = new MessageServer();

        get("/messages", (req,res) -> {
            List<Message> messages = ms.getMessages();
            if (messages.size() == 0) {
                res.status(404);
                res.type("text/plain");
                return "Messages not found";
            }
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(messages);
        });

        post("/messages", (req,res) -> {
            if (req.body() != null && !req.body().equals("")) {
                res.status(201);
                res.type("application/json");
                return new Gson().toJson(ms.postMessage(req.body()));
            } 
            res.type("text/plain");
            return "Invalid message";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
