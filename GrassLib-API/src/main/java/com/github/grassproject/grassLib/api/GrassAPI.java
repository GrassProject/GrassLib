package com.github.grassproject.grassLib.api;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.github.grassproject.grassLib.api.events.PlayerChunkChangeEvent;
import com.github.grassproject.grassLib.api.inventory.InventoryEventHandler;
import com.github.grassproject.grassLib.api.skript.skGrass;
import com.github.grassproject.grassLib.api.utilities.BukkitUtils;
import com.github.grassproject.grassLib.api.utilities.Register;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GrassAPI {
    public static JavaPlugin plugin;
    public static SkriptAddon addon;

    public static void setupGrassLib(JavaPlugin plugin) {
        // new Metrics(plugin, 25261);
        GrassAPI.plugin = plugin;

        System.out.println("YEAH?"+BukkitUtils.INSTANCE.checkPlugin("Skript"));
        System.out.println(Bukkit.getPluginManager().getPlugin("Skript").getPluginMeta().getName());
        if (BukkitUtils.INSTANCE.checkPlugin("Skript")) {
            System.out.println("YEAH!");
            addon= Skript.registerAddon(plugin);
            skGrass.init();
        } else {
            System.out.println("NO YEAH!");
        }

        new Register(plugin)
                .resistEventListener(new Listener() {
                    private final Map<UUID, Chunk> playerChunk = new HashMap<>();
                    @EventHandler
                    public void onMove(PlayerMoveEvent e) {
                        UUID playerId = e.getPlayer().getUniqueId();
                        Chunk chunk = playerChunk.get(playerId);

                        if (chunk == null || !chunk.equals(e.getPlayer().getLocation().getChunk())) {
                            Bukkit.getScheduler().runTask(plugin, () -> {
                                Bukkit.getPluginManager().callEvent(
                                        new PlayerChunkChangeEvent(
                                                chunk,
                                                e.getPlayer().getLocation().getChunk(),
                                                e.getPlayer()
                                        ));
                            });
                            playerChunk.put(playerId, e.getPlayer().getLocation().getChunk());
                        }
                    }
                })
                .resistEventListener(new InventoryEventHandler());
    }
}
