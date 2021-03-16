package edu.escuelaing.arep.logservice.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseConnection {

    private MongoClientURI uri;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private static DatabaseConnection instance = new DatabaseConnection();

    private DatabaseConnection(){
        this.uri = new MongoClientURI("mongodb://angi:angi123@mongodb:27017/?serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&authSource=logservicedb&authMechanism=SCRAM-SHA-256&3t.uriVersion=3");
        this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase("logservicedb");
        this.collection = database.getCollection("messages");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public static DatabaseConnection getInstance() {
        return instance;
    }
}
