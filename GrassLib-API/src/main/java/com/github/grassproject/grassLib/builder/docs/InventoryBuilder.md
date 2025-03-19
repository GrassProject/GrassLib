```java
import com.github.grassproject.grassLib.builder.InventoryBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryExample {

    // 📌 Open a custom chest inventory
    public void openCustomChestInventory(Player player) {
        // Create a chest type inventory
        Inventory inv = new InventoryBuilder()
                .setType(InventoryType.CHEST) // Set the inventory type to chest
                .setTitle("Custom Chest Inventory") // Set the title of the inventory
                .setSize(27) // Set the size of the inventory to 27 slots
                .setItem(13, new ItemStack(Material.PLAYER_HEAD)) // Add a player head to slot 13 (center)
                .build(); // Build the inventory

        player.openInventory(inv); // Open the inventory for the player
    }

    // 📌 Open a custom hopper inventory
    public void openCustomHopperInventory(Player player) {
        // Create a hopper type inventory (default size is 5 slots)
        Inventory inv = new InventoryBuilder()
                .setType(InventoryType.HOPPER) // Set the inventory type to hopper
                .setTitle("Custom Hopper Inventory") // Set the title of the inventory
                .setItem(2, new ItemStack(Material.DIAMOND)) // Add a diamond to slot 2
                .build(); // Build the inventory

        player.openInventory(inv); // Open the inventory for the player
    }

    // 📌 Open an inventory where all slots are filled with a specific item
    public void openFilledInventory(Player player) {
        // Create a chest type inventory and fill all slots with emeralds
        Inventory inv = new InventoryBuilder()
                .setType(InventoryType.CHEST) // Set the inventory type to chest
                .setTitle("Filled Inventory") // Set the title of the inventory
                .setSize(27) // Set the size of the inventory to 27 slots
                .fill(new ItemStack(Material.EMERALD)) // Fill all slots with emeralds
                .build(); // Build the inventory

        player.openInventory(inv); // Open the inventory for the player
    }
}

```