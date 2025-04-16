package com.github.grassproject.grassLib.api.utilities;

import com.github.grassproject.grassLib.api.annotation.ComingSoon;
import com.github.grassproject.grassLib.api.annotation.command.Command;
import com.github.grassproject.grassLib.api.annotation.command.Permission;
import com.github.grassproject.grassLib.api.exception.NoPermissionForCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Method;
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

    @Deprecated
    public Register addCommand(Object instance) {
        Class<?> clazz = instance.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            Command commandAnnotation = method.getAnnotation(Command.class);
            if (commandAnnotation == null) continue;

            Permission permissionAnnotation = method.getAnnotation(Permission.class);
            String commandName = commandAnnotation.name().toLowerCase();

            org.bukkit.command.PluginCommand pluginCommand = plugin.getCommand(commandName);
            if (pluginCommand == null) continue;

            pluginCommand.setExecutor((sender, command, label, args) -> {
                if (sender instanceof Player && permissionAnnotation != null) {
                    String perm = permissionAnnotation.permission();
                    if (!sender.hasPermission(perm)) {
//                        throw new NoPermissionForCommand(perm);
                        return true;
                    }
                }

                try {
                    method.invoke(instance, sender, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            });
        }
        return this;
    }
}
