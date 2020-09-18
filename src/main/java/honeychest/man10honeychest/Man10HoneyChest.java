package honeychest.man10honeychest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EventListener;

public final class Man10HoneyChest extends JavaPlugin {

    String prefix = "§6§l[§a§lMa§f§ln§d§l10§6§lHChest]§r";

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Man10HoneyChest起動しました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Man10HoneyChestを停止します");
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            if (e.getClickedInventory().getViewers().contains("§H§o§n§e§y§8Chest")) {
                if (e.getWhoClicked().hasPermission("man10honeychest.op")) {
                    return;
                }
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                String test = e.getWhoClicked().getName();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tell " + test + "あいうえお");
                return;
            }
        }catch (NullPointerException ee){
        }
    }
}
