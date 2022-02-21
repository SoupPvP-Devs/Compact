package me.ninetyeightping.compact.controller.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.Controller;
import me.ninetyeightping.compact.models.impl.Profile;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class ProfileController extends Controller<Profile> {

    public List<Profile> cache = new CopyOnWriteArrayList<>();

    private MongoCollection<Document> mongoCollection;

    public ProfileController(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);


        this.mongoCollection = mongoCollection;

        refresh();


    }

    public void create(Profile profile) {

        ForkJoinPool.commonPool().execute(() -> {
            System.out.println("Sent profile to mongo : " + profile.getUuid() + ".");

            profile.save();
            refresh();
        });


    }

    @Override
    public void save(Profile profile) {
        Document parsed = Document.parse(profile.construct());
        parsed.remove("_id");

        Document query = new Document("_id", profile.uuid);
        Document set = new Document("$set", parsed);

        mongoCollection.updateOne(query, set, new UpdateOptions().upsert(true));
    }

    @Override
    public void refresh() {
        cache = mongoCollection.find().into(new ArrayList<>()).stream().map(document -> Compact.getGson().fromJson(document.toJson(), Profile.class)).collect(Collectors.toList());
    }

    @Override
    public boolean exists(String id) {
        return cache.stream().filter(profile -> profile.getUuid().equalsIgnoreCase(id)).findFirst().orElse(null) != null;
    }

    public Profile getByName(String id) {
        return cache.stream().filter(profile -> profile.getUsername().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    @Override
    public Profile getById(String id) {
        return cache.stream().filter(profile -> profile.getUuid().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
