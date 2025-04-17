package com.github.grassproject.grassLib.api.inventory;

import com.github.grassproject.grassLib.api.builder.InventoryBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class GrassInventory implements InventoryHolder {
    protected final Inventory inventory;

    protected GrassInventory(InventoryBuilder builder) {
        this.inventory = builder.setHolder(this).build();
        initializeItems();
    }

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

    protected void onClick(InventoryClickEvent event) {}

    protected void onDrag(InventoryDragEvent event) {}

    protected void onClose(InventoryCloseEvent event) {}

    protected void onOpen(InventoryOpenEvent event) {}

    // protected void onPageChange(int page) {}

    public static InventoryBuilder builder() {
        return new InventoryBuilder();
    }
}