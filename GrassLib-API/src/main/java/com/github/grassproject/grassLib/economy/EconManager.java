package com.github.grassproject.grassLib.economy;

import com.github.grassproject.grassLib.exception.NotFoundPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EconManager {
    private static Economy econ;
    public static boolean setupEcon(JavaPlugin plugin) throws NotFoundPlugin {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new NotFoundPlugin("Vault");
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static double getBalance(OfflinePlayer player) {
        Economy economy = getEcon();
        return economy.getBalance(player);
    }

    public static void takeMoney(OfflinePlayer player, double amount) {
        Economy economy = getEcon();
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
