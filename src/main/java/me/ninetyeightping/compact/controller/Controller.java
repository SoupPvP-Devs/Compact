package me.ninetyeightping.compact.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;

import javax.naming.ldap.Control;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Controller<T> {

    private final MongoCollection<Document> mongoCollection;


    public Controller (MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;

    }

    public abstract void save(T t);

    public abstract void refresh();

    public void delete(String id) {
        mongoCollection.deleteOne(Filters.eq("_id", id));
    }

    public abstract boolean exists(String id);

    public abstract T getById(String id);
}
