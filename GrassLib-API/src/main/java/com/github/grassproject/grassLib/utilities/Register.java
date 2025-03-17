package com.github.grassproject.grassLib.utilities;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public record Register(JavaPlugin plugin) {
    public Register resistEventListener(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        return this;
    }

    public Register resistCommandExecutor(String command, CommandExecutor executor) {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(executor);
        return this;
    }

    public Register resistTabCompleter(String command, TabCompleter completer) {
        Objects.requireNonNull(plugin.getCommand(command)).setTabCompleter(completer);
        return this;
    }

    public Register resistTabExecutor(String command, TabExecutor executor) {
        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(executor);
        Objects.requireNonNull(plugin.getCommand(command)).setTabCompleter(executor);
        return this;
    }
}
