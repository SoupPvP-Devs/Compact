package me.ninetyeightping.compact.controller.impl.grants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.UuidCodec;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public abstract class Grantable<T> {
    public UUID uuid;
    public UUID target;
    public UUID executor;
    public String reason;
    public Long duration;
    public Long addedAt;
    public String removedReason;
    public Long removedAt;
    public UUID removedBy;

    public abstract T getGrantable();

}
