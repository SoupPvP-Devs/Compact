package me.ninetyeightping.compact.controller.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.Controller;
import me.ninetyeightping.compact.models.impl.NetworkServer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class NetworkServerController extends Controller<NetworkServer> {

    public List<NetworkServer> cache = new CopyOnWriteArrayList<>();

    public MongoCollection<Document> collection;

    public NetworkServerController(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);

        this.collection = mongoCollection;

        refresh();
    }

    @Override
    public void save(NetworkServer networkServer) {
        Document parsed = Document.parse(networkServer.construct());
        parsed.remove("_id");

        Document query = new Document("_id", networkServer.getId());
        Document set = new Document("$set", parsed);

        collection.updateOne(query, set, new UpdateOptions().upsert(true));
    }

    @Override
    public void refresh() {
        cache = collection.find().into(new ArrayList<>()).stream().map(document -> Compact.getGson().fromJson(document.toJson(), NetworkServer.class)).collect(Collectors.toList());
    }

    @Override
    public boolean exists(String id) {
        return getById(id) != null;
    }

    @Override
    public NetworkServer getById(String id) {
        return cache.stream().filter(networkServer -> networkServer.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
