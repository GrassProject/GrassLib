package com.github.grassproject.grassLib.api.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author MrJimin
 * @apiNote InventoryBuilder for creating custom inventories
 */
public class InventoryBuilder {
    private Component title = Component.text("Custom Inventory");
    private InventoryType type = InventoryType.CHEST;
    private int size = 9;
    private InventoryHolder holder;
    private final Map<Integer, ItemStack> items = new HashMap<>();

    public InventoryBuilder() {
    }

    @NotNull
    public InventoryBuilder setTitle(@NotNull Component title) {
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        return this;
    }

    @NotNull
    public InventoryBuilder setType(@NotNull InventoryType type) {
        this.type = Objects.requireNonNull(type, "Inventory type cannot be null");
        this.size = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        return this;
    }

    @NotNull
    public InventoryBuilder setSize(int size) {
        if (this.type == InventoryType.CHEST) {
            this.size = Math.min(Math.max(size, 9), 54);
            if (this.size % 9 != 0) {
                this.size = ((this.size / 9) + 1) * 9;
            }
        }
        return this;
    }

    @NotNull
    public InventoryBuilder setHolder(InventoryHolder holder) {
        this.holder = holder;
        return this;
    }

    @NotNull
    public InventoryBuilder setItem(int slot, @NotNull ItemStack item) {
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        if (slot >= 0 && slot < maxSlots) {
            this.items.put(slot, Objects.requireNonNull(item, "Item cannot be null").clone());
        }
        return this;
    }

    @NotNull
    public InventoryBuilder setItemRange(int startSlot, int endSlot, @NotNull ItemStack item) {
        if (startSlot > endSlot) {
            throw new IllegalArgumentException("Start slot (" + startSlot + ") cannot be greater than end slot (" + endSlot + ")");
        }
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        int start = Math.max(0, startSlot);
        int end = Math.min(maxSlots - 1, endSlot);
        ItemStack clonedItem = Objects.requireNonNull(item, "Item cannot be null").clone();
        for (int slot = start; slot <= end; slot++) {
            this.items.put(slot, clonedItem.clone());
        }
        return this;
    }

    @NotNull
    public InventoryBuilder fill(@NotNull ItemStack item) {
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        ItemStack clonedItem = Objects.requireNonNull(item, "Item cannot be null").clone();
        for (int i = 0; i < maxSlots; i++) {
            this.items.put(i, clonedItem.clone());
        }
        return this;
    }

    @NotNull
    public InventoryBuilder modifyInventory(@NotNull Consumer<Inventory> modifier) {
        Objects.requireNonNull(modifier, "Modifier cannot be null");
        Inventory tempInventory = build();
        modifier.accept(tempInventory);
        this.items.clear();
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        for (int i = 0; i < maxSlots; i++) {
            ItemStack item = tempInventory.getItem(i);
            if (item != null && !item.getType().isAir()) {
                this.items.put(i, item.clone());
            }
        }
        return this;
    }

    @NotNull
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