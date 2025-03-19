package com.github.grassproject.grassLib.builder;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.github.grassproject.grassLib.exception.NotFoundPlugin;
import com.github.grassproject.grassLib.utilities.component.Str2Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author MrJimin
 * @apiNote InventoryBuilder with ProtocolLib for opening custom inventories
 */
public class InventoryBuilder {
    private String title;
    private InventoryType type;
    private int size;
    private final Map<Integer, ItemStack> items;
    private final ProtocolManager protocolManager;

    /**
     * Creates an InventoryBuilder for a chest inventory with a specified size.
     *
     * @param size The size of the chest inventory (must be a multiple of 9, max 54)
     */
    public InventoryBuilder(int size) throws NotFoundPlugin {
        this.size = Math.min(Math.max(size, 9), 54);
        this.title = "Custom Chest";
        this.type = InventoryType.CHEST;
        this.items = new HashMap<>();
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib")==null) {
            throw new NotFoundPlugin("ProtocolLib");
        }
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    /**
     * Creates an InventoryBuilder for a specific inventory type.
     *
     * @param type The type of inventory (e.g., HOPPER, FURNACE)
     */
    public InventoryBuilder(InventoryType type) {
        this.type = type;
        this.title = type.getDefaultTitle();
        this.size = type.getDefaultSize();
        this.items = new HashMap<>();
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    /**
     * Sets the title of the inventory.
     *
     * @param title The title to set
     * @return InventoryBuilder instance for chaining
     */
    public InventoryBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the size of the inventory (only applicable for CHEST type).
     *
     * @param size The size to set (must be a multiple of 9, max 54)
     * @return InventoryBuilder instance for chaining
     */
    public InventoryBuilder setSize(int size) {
        if (this.type == InventoryType.CHEST) {
            this.size = Math.min(Math.max(size, 9), 54);
        }
        return this;
    }

    /**
     * Adds an item to a specific slot in the inventory.
     *
     * @param slot The slot index to place the item
     * @param item The ItemStack to add
     * @return InventoryBuilder instance for chaining
     */
    public InventoryBuilder setItem(int slot, ItemStack item) {
        if (slot >= 0 && slot < (type == InventoryType.CHEST ? this.size : type.getDefaultSize())) {
            this.items.put(slot, item.clone());
        }
        return this;
    }

    /**
     * Fills the inventory with a specific item.
     *
     * @param item The ItemStack to fill with
     * @return InventoryBuilder instance for chaining
     */
    public InventoryBuilder fill(ItemStack item) {
        int maxSlots = type == InventoryType.CHEST ? this.size : type.getDefaultSize();
        for (int i = 0; i < maxSlots; i++) {
            this.items.put(i, item.clone());
        }
        return this;
    }

    /**
     * Modifies the inventory with a custom consumer after building.
     *
     * @param modifier Consumer to modify the inventory
     * @return InventoryBuilder instance for chaining
     */
    public InventoryBuilder modifyInventory(Consumer<Inventory> modifier) {
        Inventory tempInventory = buildInventory();
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

    /**
     * Builds the inventory without opening it.
     *
     * @return The constructed Inventory
     */
    private Inventory buildInventory() {
        Inventory inv;
        if (this.type == InventoryType.CHEST) {
            inv = Bukkit.createInventory(null, this.size, Str2Component.Companion.toComponent(this.title));
        } else {
            inv = Bukkit.createInventory(null, this.type, Str2Component.Companion.toComponent(this.title));
        }

        for (Map.Entry<Integer, ItemStack> entry : this.items.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue());
        }

        return inv;
    }

    /**
     * Opens the inventory for a player using ProtocolLib.
     *
     * @param player The player to open the inventory for
     * @return The opened Inventory object
     */
    public Inventory open(Player player) {
        Inventory inv = buildInventory();
        sendOpenWindowPacket(player, inv);
        player.openInventory(inv);
        return inv;
    }

    /**
     * Sends an OPEN_WINDOW packet to the player using ProtocolLib.
     *
     * @param player The player to send the packet to
     * @param inv    The inventory being opened
     */
    private void sendOpenWindowPacket(Player player, Inventory inv) {
        PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.OPEN_WINDOW);

        int windowId = (player.getEntityId() % 100) + 1;
        packet.getIntegers().write(0, windowId);

        String inventoryType = switch (this.type) {
            case HOPPER -> "minecraft:hopper";
            case FURNACE -> "minecraft:furnace";
            case DISPENSER -> "minecraft:dispenser";
            case DROPPER -> "minecraft:dropper";
            case BREWING -> "minecraft:brewing_stand";
            case ANVIL -> "minecraft:anvil";
            default -> "minecraft:container";
        };

        packet.getStrings().write(0, inventoryType);
        packet.getChatComponents().write(0, com.comphenix.protocol.wrappers.WrappedChatComponent.fromText(this.title));
        packet.getIntegers().write(1, inv.getSize());

        protocolManager.sendServerPacket(player, packet);
    }
}