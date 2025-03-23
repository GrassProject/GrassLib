package com.github.grassproject.grassLib.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author MrJimin
 * @apiNote InventoryBuilder
 */
public class InventoryBuilder {
    private Component title = Component.text("Custom Inventory");
    private InventoryType type = InventoryType.CHEST;
    private int size = 9;
    private InventoryHolder holder;
    private final Map<Integer, ItemStack> items = new HashMap<>();

    public InventoryBuilder setTitle(Component title) {
        this.title = title;
        return this;
    }

    public InventoryBuilder setType(InventoryType type) {
        this.type = type;
        this.size = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        return this;
    }

    public InventoryBuilder setSize(int size) {
        if (this.type == InventoryType.CHEST) {
            this.size = Math.min(Math.max(size, 9), 54);
        }
        return this;
    }

    public InventoryBuilder setHolder(InventoryHolder holder) {
        this.holder = holder;
        return this;
    }

    public InventoryBuilder setItem(int slot, ItemStack item) {
        if (slot >= 0 && slot < (type == InventoryType.CHEST ? this.size : type.getDefaultSize())) {
            this.items.put(slot, item.clone());
        }
        return this;
    }

    public InventoryBuilder setItemRange(int startSlot, int endSlot, ItemStack item) {
        if (startSlot > endSlot) {
            throw new IllegalArgumentException("Start slot cannot be greater than end slot");
        }
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        int start = Math.max(0, startSlot);
        int end = Math.min(maxSlots - 1, endSlot);
        for (int slot = start; slot <= end; slot++) {
            this.items.put(slot, item.clone());
        }
        return this;
    }

    public InventoryBuilder fill(ItemStack item) {
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        for (int i = 0; i < maxSlots; i++) {
            this.items.put(i, item.clone());
        }
        return this;
    }

    public InventoryBuilder modifyInventory(Consumer<Inventory> modifier) {
        Inventory tempInventory = build();
        modifier.accept(tempInventory);
        this.items.clear();
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        for (int i = 0; i < maxSlots; i++) {
            ItemStack item = tempInventory.getItem(i);
            if (item != null) {
                this.items.put(i, item.clone());
            }
        }
        return this;
    }

    public Inventory build() {
        Inventory inv = this.type == InventoryType.CHEST
                ? Bukkit.createInventory(this.holder, this.size, this.title)
                : Bukkit.createInventory(this.holder, this.type, this.title);

        for (Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue());
        }

        return inv;
    }
}