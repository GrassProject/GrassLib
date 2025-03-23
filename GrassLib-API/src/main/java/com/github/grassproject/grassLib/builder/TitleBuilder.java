package com.github.grassproject.grassLib.builder;

import com.github.grassproject.grassLib.utilities.component.StringExt;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;

public class TitleBuilder {
    private final BukkitAudiences audiences;
    private final NamespacedKey key;
    private Component title = Component.empty();
    private Component subTitle = Component.empty();
    private long fadeInSeconds = 0L;
    private long staySeconds = 0L;
    private long fadeOutSeconds = 0L;

    public TitleBuilder(JavaPlugin plugin, String key) {
        this.audiences = BukkitAudiences.create(plugin);
        this.key = new NamespacedKey(plugin, key);
    }

    public NamespacedKey getKey() {
        return key;
    }

    public TitleBuilder title(String title) {
        this.title = title != null ? StringExt.Companion.toMiniMessage(title) : Component.empty();
        return this;
    }

    public TitleBuilder title(Component title) {
        this.title = title != null ? title : Component.empty();
        return this;
    }

    public TitleBuilder subTitle(String subTitle) {
        this.subTitle = subTitle != null ? StringExt.Companion.toMiniMessage(subTitle) : Component.empty();
        return this;
    }

    public TitleBuilder subTitle(Component subTitle) {
        this.subTitle = subTitle != null ? subTitle : Component.empty();
        return this;
    }

    public TitleBuilder fadeIn(long seconds) {
        this.fadeInSeconds = Math.max(seconds, 0);
        return this;
    }

    public TitleBuilder stay(long seconds) {
        this.staySeconds = Math.max(seconds, 0);
        return this;
    }

    public TitleBuilder fadeOut(long seconds) {
        this.fadeOutSeconds = Math.max(seconds, 0);
        return this;
    }

    private Title build() {
        Title.Times times = Title.Times.times(
                Duration.ofSeconds(fadeInSeconds),
                Duration.ofSeconds(staySeconds),
                Duration.ofSeconds(fadeOutSeconds)
        );
        return Title.title(this.title, this.subTitle, times);
    }

    public void sendToPlayer(Player player) {
        audiences.player(player).showTitle(build());
    }

    public void broadcast() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            audiences.player(player).showTitle(build());
        }
    }

    public void removeTitle(Player player) {
        audiences.player(player).clearTitle();
    }

    public void setTitle(String title) {
        this.title = title != null ? StringExt.Companion.toMiniMessage(title) : Component.empty();
    }

    public void setTitle(Component title) {
        this.title = title != null ? title : Component.empty();
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle != null ? StringExt.Companion.toMiniMessage(subTitle) : Component.empty();
    }

    public void setSubTitle(Component subTitle) {
        this.subTitle = subTitle != null ? subTitle : Component.empty();
    }

    public void removeTitle() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            audiences.player(player).clearTitle();
        }
    }
}