package me.ninetyeightping.compact.controller.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.Controller;
import me.ninetyeightping.compact.models.impl.Profile;
import me.ninetyeightping.compact.models.impl.Rank;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RankController extends Controller<Rank> {
    public List<Rank> cache = new CopyOnWriteArrayList<>();

    private MongoCollection<Document> mongoCollection;

    public RankController(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);


        this.mongoCollection = mongoCollection;

        refresh();

    }

    public void create(Rank rank) {
        rank.save();
        refresh();
    }

    @Override
    public void save(Rank rank) {
        Document parsed = Document.parse(rank.construct());
        parsed.remove("_id");

        Document query = new Document("_id", rank.getId());
        Document set = new Document("$set", parsed);

        mongoCollection.updateOne(query, set, new UpdateOptions().upsert(true));
        refresh();
    }

    @Override
    public void refresh() {
        cache = mongoCollection.find().into(new ArrayList<>()).stream().map(document -> Compact.getGson().fromJson(document.toJson(), Rank.class)).collect(Collectors.toList());
    }

    @Override
    public boolean exists(String id) {
        return cache.stream().filter(rank -> rank.getId().equalsIgnoreCase(id)).findFirst().orElse(null) != null;
    }

    @Override
    public Rank getById(String id) {
        return cache.stream().filter(rank -> rank.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}

