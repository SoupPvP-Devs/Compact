package me.ninetyeightping.compact.controller.impl.grants.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.Controller;
import me.ninetyeightping.compact.models.impl.Rank;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RankGrantController extends Controller<RankGrant> {
    public List<RankGrant> cache = new CopyOnWriteArrayList<>();

    private final MongoCollection<Document> mongoCollection;

    public RankGrantController(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);


        this.mongoCollection = mongoCollection;

        refresh();

        Bukkit.getScheduler().runTaskTimer(Compact.getInstance(), () -> {

            for (RankGrant rankGrant : cache) {

                if (rankGrant.getRemainingTime() <= 0 && rankGrant.isActive()) {

                    rankGrant.setRemovedReason("Expired");
                    rankGrant.setRemovedAt(System.currentTimeMillis());
                    rankGrant.setRemovedBy(CompactAPI.getConsoleUUID());
                }
            }
        }, 0L, 20L);

    }

    public void create(RankGrant rankGrant) {
        rankGrant.save();
        refresh();
    }

    @Override
    public void save(RankGrant rankGrant) {
        Document parsed = Document.parse(rankGrant.construct());
        parsed.remove("_id");

        Document query = new Document("_id", rankGrant.getUuid().toString());
        Document set = new Document("$set", parsed);

        mongoCollection.updateOne(query, set, new UpdateOptions().upsert(true));
    }

    @Override
    public void refresh() {
        cache = mongoCollection.find().into(new ArrayList<>()).stream().map(document -> Compact.getGson().fromJson(document.toJson(), RankGrant.class)).collect(Collectors.toList());
    }

    //need these but in all honesty will NEVER use them :^)

    @Override
    public boolean exists(String id) {
        return cache.stream().filter(rankGrant -> rankGrant.getUuid().toString().equalsIgnoreCase(id)).findFirst().orElse(null) != null;
    }

    @Override
    public RankGrant getById(String id) {
        return null;
    }


}
