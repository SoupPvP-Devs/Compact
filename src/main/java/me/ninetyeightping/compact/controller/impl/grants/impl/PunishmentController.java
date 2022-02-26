package me.ninetyeightping.compact.controller.impl.grants.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.CompactAPI;
import me.ninetyeightping.compact.controller.Controller;
import me.ninetyeightping.compact.redis.backend.PacketHandler;
import me.ninetyeightping.compact.redis.frontend.impl.GlobalStaffMessagePacket;
import me.ninetyeightping.compact.redis.frontend.impl.PunishmentKickPacket;
import me.ninetyeightping.compact.util.TimeUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class PunishmentController extends Controller<Punishment> {

    public List<Punishment> cache = new CopyOnWriteArrayList<>();

    private final MongoCollection<Document> mongoCollection;


    public PunishmentController(MongoCollection<Document> mongoCollection) {
        super(mongoCollection);

        this.mongoCollection = mongoCollection;

        refresh();

        Bukkit.getScheduler().runTaskTimer(Compact.getInstance(), () -> {
            for (Punishment punishment : cache) {

                if (punishment.getRemainingTime() <= 0 && punishment.isActive()) {

                    punishment.setRemovedReason("Expired");
                    punishment.setRemovedAt(System.currentTimeMillis());
                    punishment.setRemovedBy(CompactAPI.getConsoleUUID());
                }
            }
        }, 0L, 20L);
    }
    public void dispatch(Punishment punishment, boolean silent) {
        save(punishment);
        PacketHandler.sendToAll(new GlobalStaffMessagePacket((silent ? "&7[Silent] " : "") + (CompactAPI.getColoredDisplay(punishment.getExecutor()) +
                " &ahas " + punishment.getGrantable().getAdded() +  " " + CompactAPI.getColoredDisplay(punishment.getTarget()) + " &afor &f"
                + punishment.getReason() +
                " &7(" + (punishment.getDuration() == Long.MAX_VALUE ? "Forever" : TimeUtil.formatDuration(punishment.getDuration())) + ")")));
        refresh();

        PacketHandler.sendToAll(new PunishmentKickPacket(punishment));


    }

    @Override
    public void save(Punishment punishment) {
        Document parsed = Document.parse(punishment.construct());
        parsed.remove("_id");

        Document query = new Document("_id", punishment.getUuid().toString());
        Document set = new Document("$set", parsed);

        mongoCollection.updateOne(query, set, new UpdateOptions().upsert(true));
    }

    @Override
    public void refresh() {
        cache = mongoCollection.find().into(new ArrayList<>()).stream().map(document -> Compact.getGson().fromJson(document.toJson(), Punishment.class)).collect(Collectors.toList());
    }

    //dont neeeeeeed

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public Punishment getById(String id) {
        return null;
    }
}
