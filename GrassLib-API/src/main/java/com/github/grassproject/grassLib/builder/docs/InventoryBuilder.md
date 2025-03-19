```java
import com.github.grassproject.grassLib.builder.InventoryBuilder;
import com.github.grassproject.grassLib.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryExample {
    public void openPlayerHeadInventory(Player player) {

        Inventory inv = new InventoryBuilder(27)
                .setTitle("Custom Chest Inventory")
                .setItem(13, ItemStack);
                // .open(player);
        // Inventory inv = inventoryBuilder.open(player);

        Inventory inv = new InventoryBuilder(InventoryType.HOPPER)
                .setTitle("Custom Hopper Inventory")
                .setItem(2, ItemStack);
                // .open(player);
        // Inventory inv = inventoryBuilder.open(player);
    }
}
```