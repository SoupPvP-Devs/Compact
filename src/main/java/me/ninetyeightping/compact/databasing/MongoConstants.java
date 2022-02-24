package me.ninetyeightping.compact.databasing;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.ninetyeightping.compact.Compact;
import org.bson.Document;

public class MongoConstants {

    public static MongoClient client;
    public static MongoDatabase mongoDatabase;
    public static MongoCollection<Document> profiles;
    public static MongoCollection<Document> ranks;
    public static MongoCollection<Document> rankGrants;
    public static MongoCollection<Document> punishments;
    public static MongoCollection<Document> networkServer;

    public static void loadConnections() {
        client = new MongoClient(new MongoClientURI(Compact.getInstance().getConfig().getString("uri")));
        mongoDatabase = client.getDatabase("Compact");

        profiles = mongoDatabase.getCollection("profiles");
        ranks = mongoDatabase.getCollection("ranks");
        rankGrants = mongoDatabase.getCollection("rankGrants");
        punishments = mongoDatabase.getCollection("punishments");
        networkServer = mongoDatabase.getCollection("networkServers");
    }


}
