package edu.escuelaing.arep.logservice.service;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import edu.escuelaing.arep.logservice.model.Message;
import edu.escuelaing.arep.logservice.persistence.DatabaseConnection;
import org.bson.Document;
import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageServer {

    private DatabaseConnection dbc;
    private MongoCollection<Document> collection;

    public MessageServer () {
        this.dbc = DatabaseConnection.getInstance();
        this.collection = dbc.getCollection();
    }

    public List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();
        for (Document d : collection.find()) {
            messages.add(new Message(d.get("date").toString(), d.get("message").toString()));
        }
        return getLastMessages(messages, 10);
    }

    public Message postMessage(String message){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Message newMessage = new Message(dateFormat.format(date), message);
        Document document = new Document();
        document.put("message", newMessage.getMessage());
        document.put("date", newMessage.getDate());
        collection.insertOne(document);
        return newMessage;
    }

    private List<Message> getLastMessages (List<Message> messages, int n){
        if ( messages.size() < n ) n = messages.size();
        List<Message> lastMessages = new ArrayList<>();
        for (int i = messages.size() - 1 ; i > messages.size() - 1 - n ; i--)lastMessages.add(messages.get(i));
        return lastMessages;
    }

}