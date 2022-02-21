package me.ninetyeightping.compact.models.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.injection.InjectionUtil;
import me.ninetyeightping.compact.models.Model;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
public class Rank extends Model {
    public String id;
    public String color;
    public String displayName;
    public int displayWeight;
    public int internalWeight;
    public boolean staff;
    public String prefix;

    public List<String> perms;
    public List<String> parents;

    @Override
    public String construct() {
        return Compact.getGson().toJson(this);
    }

    @Override
    public void save() {
        InjectionUtil.get(RankController.class).save(this);
    }
}
