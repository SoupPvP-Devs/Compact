package me.ninetyeightping.compact.controller.impl.grants.impl;

import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.grants.Grantable;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.Model;
import me.ninetyeightping.compact.models.impl.Rank;

import java.util.UUID;

public class RankGrant extends Grantable<Rank> {

    public Rank grantedRank;

    public RankGrant(UUID target, UUID exec, String reason, Long duration, Rank rank) {
        super(UUID.randomUUID(), target, exec, reason, duration, System.currentTimeMillis(), null, 0L, null);

        this.grantedRank = rank;
    }

    public String construct() {
        return Compact.getGson().toJson(this);
    }

    public void save() {
        InjectionUtil.get(RankGrantController.class).save(this);
    }

    public boolean isActive(){
        return removedAt == 0L;
    }

    public long getRemainingTime(){
        return this.addedAt + this.duration - System.currentTimeMillis();
    }


    @Override
    public Rank getGrantable() {
        return grantedRank;
    }
}
