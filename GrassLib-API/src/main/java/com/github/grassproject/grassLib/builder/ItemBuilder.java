package com.github.grassproject.grassLib.builder;

import com.github.grassproject.grassLib.utilities.component.StringExt;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author apo2073
 * @apiNote ItemBuilder
* */
public class ItemBuilder {
    private final ItemStack itemStack;
    private Integer amount;
    private ItemMeta itemMeta;
    private List<Component> lore;

    /**
     * @param material The Material of The Item
     * */
    public ItemBuilder(Material material) {this.itemStack=new ItemStack(material);}

    /**
     * @param material The Material of The Item
     * @param amount Amount of The Item
     * */
    public ItemBuilder(Material material, int amount) {
        this.itemStack=new ItemStack(material, amount);
        this.amount=amount;
    }

    /**
     * @param item Get ItemBuilder from ItemStack */
    public ItemBuilder(ItemStack item) {
        this.itemStack=item;
        this.itemMeta=item.getItemMeta();
    }

    /**
     * @return ItemMeta
     * */
    public ItemMeta getItemMeta() {
        return itemMeta;
    }
    /**
     * @param meta ItemMeta to set
     * */
    public ItemBuilder setItemMeta(ItemMeta meta) {
        this.itemMeta=meta;
        return this;
    }

    /**
     * @param displayName String value of the item display name
     * */
    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.displayName(StringExt.Companion.toMiniMessage(displayName));
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }

    /**
     * @param flags Flags to add
     * */
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

    /**
     * @return ItemStack of item
     * */
    public ItemStack build() {
        this.itemStack.setItemMeta(itemMeta);
        this.itemStack.setAmount(amount);
        return itemStack;
    }
}