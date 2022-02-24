package me.ninetyeightping.compact.models.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.NetworkServerController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.Model;

@Data
@AllArgsConstructor
public class NetworkServer extends Model {
    public String id;
    public String displayName;
    public int players;
    public boolean responding;
    public Long lastResponded;
    public Long lastHeartbeatRan;

    @Override
    public String construct() {
        return Compact.getGson().toJson(this);
    }

    @Override
    public void save() {
        InjectionUtil.get(NetworkServerController.class).save(this);

    }
}
