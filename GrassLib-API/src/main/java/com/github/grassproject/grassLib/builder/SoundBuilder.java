package com.github.grassproject.grassLib.builder;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundBuilder {
    private String soundString;
    private Sound soundEnum;
    private float volume;
    private float pitch;
    private Location location;
    private Player player;

    public SoundBuilder() {
        this.volume = 1.0f;
        this.pitch = 1.0f;
    }

    public SoundBuilder setSound(Sound sound) {
        this.soundEnum = sound;
        this.soundString = null;
        return this;
    }

    public SoundBuilder setSound(String sound) {
        this.soundString = sound;
        this.soundEnum = null;
        return this;
    }

    public SoundBuilder setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public SoundBuilder setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public SoundBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public SoundBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public void playForPlayer() {
        if (player == null) {
            throw new IllegalStateException("Player가 설정되지 않았습니다!");
        }
        if (soundString == null && soundEnum == null) {
            throw new IllegalStateException("Sound가 설정되지 않았습니다!");
        }
        if (soundEnum != null) {
            player.playSound(player.getLocation(), soundEnum, volume, pitch);
        } else {
            player.playSound(player.getLocation(), soundString, volume, pitch);
        }
    }

    public void playAtLocation() {
        if (location == null) {
            throw new IllegalStateException("Location이 설정되지 않았습니다!");
        }
        if (soundString == null && soundEnum == null) {
            throw new IllegalStateException("Sound가 설정되지 않았습니다!");
        }
        if (soundEnum != null) {
            location.getWorld().playSound(location, soundEnum, volume, pitch);
        } else {
            location.getWorld().playSound(location, soundString, volume, pitch);
        }
    }

    public void play() {
        if (player != null) {
            playForPlayer();
        } else if (location != null) {
            playAtLocation();
        } else {
            throw new IllegalStateException("Player 또는 Location이 설정되지 않았습니다!");
        }
    }
}