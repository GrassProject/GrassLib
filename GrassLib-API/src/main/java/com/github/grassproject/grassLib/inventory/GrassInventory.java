package com.github.grassproject.grassLib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public abstract class GrassInventory implements InventoryHolder {
    protected final Inventory inventory;

    public GrassInventory(int size, Component title) {
        this.inventory = Bukkit.createInventory(this, size, title);
        initializeItems();
    }

    public GrassInventory(InventoryType type, Component title) {
        this.inventory = Bukkit.createInventory(this, type, title);
        initializeItems();
    }

    protected abstract void initializeItems();

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    protected boolean onClick(InventoryClickEvent event) {
        return true;
    }

    protected boolean onDrag(InventoryDragEvent event) {
        return true;
    }

    protected void onClose(InventoryCloseEvent event) {
    }
}