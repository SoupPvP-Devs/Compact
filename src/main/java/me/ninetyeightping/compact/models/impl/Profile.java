package me.ninetyeightping.compact.models.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.controller.impl.grants.impl.Punishment;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrant;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrantController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.Model;
import me.ninetyeightping.compact.general.punishments.PunishmentType;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
@AllArgsConstructor
public class Profile extends Model {
    public String uuid;
    public String username;
    public JSONObject metadata;
    public List<String> activeAddresses;

    public List<RankGrant> fetchGrants() {
        return InjectionUtil.get(RankGrantController.class).cache
                .stream()
                .filter(rankGrant -> rankGrant.getTarget().toString().equalsIgnoreCase(uuid))
                .collect(Collectors.toList());
    }

    public boolean hasActivePunishmentByType(PunishmentType type) {
        return InjectionUtil.get(PunishmentController.class)
                .cache.stream()
                .filter(punishment -> punishment.getTarget().toString().equalsIgnoreCase(uuid) && punishment.getGrantable() == type)
                .findFirst().orElse(null) != null;
    }

    public List<Punishment> getAllPunishmentsByType(PunishmentType type) {
        return InjectionUtil.get(PunishmentController.class)
                .cache.stream()
                .filter(punishment -> punishment.getTarget().toString().equalsIgnoreCase(uuid) && punishment.getGrantable() == type)
                .collect(Collectors.toList());
    }

    public Punishment getFirstPunishmentByType(PunishmentType type) {
        return InjectionUtil.get(PunishmentController.class)
                .cache.stream()
                .filter(punishment -> punishment.getTarget().toString().equalsIgnoreCase(uuid) && punishment.getGrantable() == type)
                .findFirst().orElse(null);
    }

    public Rank getHighestRank() {
        Rank highest = InjectionUtil.get(RankController.class).getById("default");
        for (RankGrant rankGrant : fetchGrants().stream().filter(RankGrant::isActive).collect(Collectors.toList())) {
            if (rankGrant.getGrantable() != null) {

                if (highest.getInternalWeight() < rankGrant.getGrantable().getInternalWeight()) {


                    highest = rankGrant.getGrantable();
                }
            }
        }
        return highest;
    }


    @Override
    public String construct() {
        return Compact.getGson().toJson(this);
    }

    @Override
    public void save() {
        InjectionUtil.get(ProfileController.class).save(this);
    }
}
