```java
import com.github.grassproject.grassLib.builder.ItemBuilder;
import org.bukkit.Material;

ItemStack item= new ItemBuilder(Material.PLAYER_HEAD, 23)
        .setDisplayName("Player Head")
        .modifierMeta(meta-> {
            ((SkullMeta) meta).setOwningPlayer(
                    Bukkit.getOfflinePlayer("APO2073")
            );
        })
        .build();
```