package com.github.grassproject.grassLib.builder;

import com.github.grassproject.grassLib.exception.EntityBuilderNullLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.function.Consumer;

public class EntityBuilder<T extends Entity> {
    private final Class<T> clazz;
    private Location location;
    private Consumer<? super T> consumer;
    private CreatureSpawnEvent.SpawnReason reason= CreatureSpawnEvent.SpawnReason.CUSTOM;

    public EntityBuilder(Class<T> tClass) {
        this(tClass, null, null);
    }

    public EntityBuilder(Class<T> tClass, Location location) {
        this(tClass, location, null);
    }

    public EntityBuilder(Class<T> tClass, Location location, Consumer<? super T> consumer) {
        this.clazz = tClass;
        this.location = location;
        this.consumer = consumer;
    }

    public EntityBuilder<T> setLocation(Location location) {
        this.location = location;
        return this;
    }

    public EntityBuilder<T> setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    public EntityBuilder<T> setSpawnReason(CreatureSpawnEvent.SpawnReason reason) {
        this.reason=reason;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Consumer<? super T> getConsumer() {
        return consumer;
    }

    public CreatureSpawnEvent.SpawnReason getReason() {
        return reason;
    }

    public T build() {
        if (location==null) throw new EntityBuilderNullLocation("There's no location to spawn!");
        var world=location.getWorld();
        return world.spawn(location, clazz, reason, consumer);
    }
}
