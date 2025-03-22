# Hello! It's GrassLib 😎
### It's a simple library to create minecraft plugin

# usage

## 1. Builder
1. ItemBuilder
```java
import com.github.grassproject.grassLib.builder.ItemBuilder;

ItemStack item= new ItemBuilder(Material.PLAYER_HEAD, 23)
        .setDisplayName("Player Head")
        .modifierMeta(meta-> {
            ((SkullMeta) meta).setOwningPlayer(
                    Bukkit.getOfflinePlayer("APO2073")
            );
        })
        .build();
```

2. EntityBuilder
```java
import com.github.grassproject.grassLib.builder.EntityBuilder;

Zombie zombie= new EntityBuilder<>(Zombie.class)
                .setConsumer(e-> {
                    e.setBaby();
                })
                .build();
```

3. InventoryBuilder
```java
import com.github.grassproject.grassLib.builder.InventoryBuilder;

Inventory i= new InventoryBuilder()
                .setType(InventoryType.CHEST)
                .setTitle("Custom Chest Inventory")
                .setSize(27)
                .modifyInventory(meta -> {)
                    meta.setItem(13, new ItemStack(Material.PLAYER_HEAD));
                })
                .setItem(13, new ItemStack(Material.PLAYER_HEAD))
                .build();
```

4. SCoreboardBuilder
```java
import com.github.grassproject.grassLib.builder.ScoreboardBuilder;

Scoreboard scoreboard = new ScoreboardBuilder(
                "boardID",
                Criteria.DUMMY,
                "title",
                DisplaySlot.SIDEBAR)
                .addObject("WELCOME", 0)
                .build();
Player.setScoreboard(scoreboard);
```

## 2. EconManager
```java
import com.github.grassproject.grassLib.economy.EconManager;

EconManager.setupEcon(JavaPlugin); // setup economy
Economy econ= EconManager.getEcon(); // get economy

EconManager.takeMoney(OfflinePlayer, double); // take money from player
EconManager.giveMoney(OfflinePlayer, double); // give money to player
EconManager.sendMoney(OfflinePlayer, OfflinePlayer, double); // send money to player
```

## 3. Register
```java
import com.github.grassproject.grassLib.utilities.Register;

Register(JavaPlugin)
    .resistEventListener(Listener)
    .resistCommandExecutor(String, CommandExecutor) // register command
    .resistTabCompleter(String, TabCompleter) // register tab completer
    .resistTabExecutor(String, TabExecutor); // register tab executor
```

## 4. Utilities
1. PluginUtil
```java
import com.github.grassproject.grassLib.utilities.PluginUtil;

PluginUtil.checkPlugin(JavaPlugin) // check plugin is enabled
```

2. StringExt
```java
import com.github.grassproject.grassLib.utilities.component.StringExt;

String.toComponent() // convert string to component
String.toMiniMessage() // convert string to MiniMessage
```

3. EventUtil
```java
import com.github.grassproject.grassLib.utilities.EventUtil;

Event.call() // call event
```

4. PlayerUtil
```java
import com.github.grassproject.grassLib.utilities.PlayerUtil;

Player.performCommandAsOP(String) // perform command as op
Player.takeItem(ItemStack, amount) // take item from player
PlayerInvenotory.full() // check inventory is full
```

5. Item Utilities
```java
import com.github.grassproject.grassLib.item.ItemUtil;

ItemUtil.createItem(Material, int, String); // create item
ItemUtil.hasPDC(ItemStack?, String, String); // check item has PDC
ItemUtil.create(ItemStack, String?, MutableList<String>?, Int, Int); // create grass item
```
 --- 
```java
ItemStack.onInteraction(JavaPlugin) {player, item, e ->
    // Event Action
}
```

6. ConfigManager
```java
ConfigManager.getConfigFile(JavaPlugin, String); // get config file
ConfigManager.getConfig(file); // get config
ConfigManager.setValue(file, path, value); // set value to config
ConfigManager.createFile(JavaPlugin, string) // create file
```

