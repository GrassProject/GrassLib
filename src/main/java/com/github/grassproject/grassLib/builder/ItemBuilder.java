package com.github.grassproject.grassLib.builder;

import com.github.grassproject.grassLib.utilities.component.Str2Component;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {
    private final ItemStack itemStack;
    private Integer amount;
    private ItemMeta itemMeta;
    private List<Component> lore;
    public ItemBuilder(Material material) {this.itemStack=new ItemStack(material);}

    public ItemBuilder(Material material, int amount) {
        this.itemStack=new ItemStack(material, amount);
        this.amount=amount;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }
    public ItemBuilder setItemMeta(ItemMeta meta) {
        this.itemMeta=meta;
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.displayName(Str2Component.Companion.toComponent(displayName));
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setLore(List<Component> lore) {
        this.itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder modifierMeta(Consumer<ItemMeta> modifier) {
        modifier.accept(this.itemMeta);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(itemMeta);
        this.itemStack.setAmount(amount);
        return itemStack;
    }
}