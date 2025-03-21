package com.github.grassproject.grassLib.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EconManager {
    private static Economy econ;
    public static boolean setupEcon(JavaPlugin plugin) throws NotFoundPlugina {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new NotFoundPlugina("Vault");
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static void takeMoney(OfflinePlayer player, double amount) {
        Economy economy=getEcon();
        economy.withdrawPlayer(player, amount);
    }

    public static void giveMoney(OfflinePlayer player, double amount) {
        Economy economy=getEcon();
        economy.depositPlayer(player, amount);
    }

    public static void sendMoney(OfflinePlayer dear, OfflinePlayer to, double amount) {
        takeMoney(dear, amount); giveMoney(to, amount);
    }
}