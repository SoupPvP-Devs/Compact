package me.ninetyeightping.compact.controller.impl.grants.impl;

import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.grants.Grantable;
import me.ninetyeightping.compact.punishments.PunishmentType;

import java.util.UUID;

public class Punishment extends Grantable<PunishmentType> {

    public PunishmentType type;

    public Punishment(UUID uuid, UUID target, UUID executor, String reason, Long duration, PunishmentType type) {
        super(uuid, target, executor, reason, duration, System.currentTimeMillis(), null, 0L, null);

        this.type = type;
    }

    public String construct() {
        return Compact.getGson().toJson(this);
    }

    public boolean isActive(){
        return removedAt == 0L;
    }

    public long getRemainingTime(){
        return this.addedAt + this.duration - System.currentTimeMillis();
    }

    @Override
    public PunishmentType getGrantable() {
        return type;
    }
}
