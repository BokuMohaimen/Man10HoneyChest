package honeychest.man10honeychest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EventListener;

public final class Man10HoneyChest extends JavaPlugin implements Listener {

    String prefix = "§6§l[§a§lMa§f§ln§d§l10§6§lHChest]§r";

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Man10HoneyChest起動しました");
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Man10HoneyChestを停止します");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mhchest")) {
            if (args.length >= 2) {
                sender.sendMessage(prefix + "引数に誤りがあります\n" + prefix + "/hchest help");
                return true;
            } else if (args[0].equalsIgnoreCase("create")) {
                Player p = (Player) sender;
                ItemStack honeyChest = new ItemStack(Material.CHEST);
                ItemMeta meta = honeyChest.getItemMeta();
                meta.setDisplayName("§8Chest");
                honeyChest.setItemMeta(meta);
                p.getInventory().addItem(honeyChest);
                sender.sendMessage(prefix + "作成しました");
                return true;
            } else if (args[0].equalsIgnoreCase("help")){
                help(sender);
                return true;
            }
            sender.sendMessage(prefix + "引数に誤りがあります\n" + prefix + "/hchest help");
            return true;
        }
        return false;
    }
    void help(CommandSender sender) {
        sender.sendMessage("§6===============§4[§5Man10HoneyChest§4]§6===============\n ");
        sender.sendMessage("§d§l/mhchest create <jail/warn/practice> <self/op/global>\n ");
        sender.sendMessage("§6==============================================");
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        e.getWhoClicked().sendMessage("さ");
        try {
            if (e.getView().getTitle().equals("§8Chest")){
                e.getWhoClicked().sendMessage("き");
                if(!(e.getRawSlot() < e.getInventory().getSize())) {
                    e.setCancelled(true);
                    return;
                }
                if (e.getWhoClicked().hasPermission("man10honeychest.op")) {
                    return;
                }
                e.getWhoClicked().sendMessage("め");
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                    String test = e.getWhoClicked().getName();
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tell " + test + " あいうえお");
                    e.getWhoClicked().sendMessage(prefix + "アイテムを取りました");
                    return;
            }
        }catch (NullPointerException ee){
        }
    }
}
