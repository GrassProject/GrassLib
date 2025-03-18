package com.github.grassproject.grassLib.builder;

import com.github.grassproject.grassLib.exception.EntityBuilderNullLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.function.Consumer;

/**
 * @author APO2073
 * @apiNote EntityBuilder
 * */
public class EntityBuilder<T extends Entity> {
    private final Class<T> clazz;
    private Location location;
    private Consumer<? super T> consumer;
    private CreatureSpawnEvent.SpawnReason reason= CreatureSpawnEvent.SpawnReason.CUSTOM;

    /**
     * @param tClass The Class of Entity to Spawn
     * */
    public EntityBuilder(Class<T> tClass) {
        this(tClass, null, null);
    }

    /**
     * @param tClass The Class of Entity to Spawn
     * @param location The Location to Spawn Entity
     * */
    public EntityBuilder(Class<T> tClass, Location location) {
        this(tClass, location, null);
    }

    /**
     * @param tClass The Class of Entity to Spawn
     * @param location The Location to Spawn Entity
     * @param consumer Entity Setting
     * */
    public EntityBuilder(Class<T> tClass, Location location, Consumer<? super T> consumer) {
        this.clazz = tClass;
        this.location = location;
        this.consumer = consumer;
    }

    /**
     * @param location The Location to Spawn Entity
    * */
    public EntityBuilder<T> setLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * @param consumer Entity Setting
     * */
    public EntityBuilder<T> setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * @param reason Entity Spawn Reason
     * */
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

    /**
     * @apiNote Build Entity Builder to Entity
     * @return Entity Class
     * */
    public T build() {
        if (location==null) throw new EntityBuilderNullLocation("There's no location to spawn!");
        var world=location.getWorld();
        return world.spawn(location, clazz, reason, consumer);
    }
}
