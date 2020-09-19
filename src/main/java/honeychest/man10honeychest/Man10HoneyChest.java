package honeychest.man10honeychest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Man10HoneyChest extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();
    String prefix = "§6§l[§a§lMa§f§ln§d§l10§6§lHChest]§r";

    public String jailrunMessage ="";
    public String jailrunCommand ="";
    public String jailplaySound ="";
    public String warnrunMessage ="";
    public String warnrunCommand ="";
    public String warnplaySound ="";

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Man10HoneyChest起動しました");
        Bukkit.getPluginManager().registerEvents(this,this);
        saveDefaultConfig();
        jailrunMessage = config.getString("jail.runMessage");
        jailrunCommand = config.getString("jail.runCommand");
        jailplaySound = config.getString("jail.playSound");
        warnrunMessage  = config.getString("warn.runMessage");
        warnrunCommand = config.getString("warn.runCommand");
        warnplaySound = config.getString("warn.playSound");
        config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Man10HoneyChestを停止します");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mam10honeychest.op")){
            sender.sendMessage("§4§lYou don't have enough permission.");
            return false;
        }
        if (command.getName().equalsIgnoreCase("mhchest")) {
            if (args.length == 0) {
                sender.sendMessage(prefix + "引数に誤りがあります\n" + prefix + "/hchest help");
                return true;
            }
            if (args.length >= 2) {
                sender.sendMessage(prefix + "引数に誤りがあります\n" + prefix + "/hchest help");
                return true;
            } else if (args[0].equalsIgnoreCase("jail")) {
                Player p = (Player) sender;
                ItemStack honeyChest = new ItemStack(Material.CHEST);
                ItemMeta meta = honeyChest.getItemMeta();
                meta.setDisplayName("§8Chest");
                honeyChest.setItemMeta(meta);
                p.getInventory().addItem(honeyChest);
                sender.sendMessage(prefix + "ジェイルチェストを作成しました");
                return true;
            } if (args[0].equalsIgnoreCase("warn")) {
                Player p = (Player) sender;
                ItemStack honeyChest = new ItemStack(Material.CHEST);
                ItemMeta meta = honeyChest.getItemMeta();
                meta.setDisplayName("§8チェスト");
                honeyChest.setItemMeta(meta);
                p.getInventory().addItem(honeyChest);
                sender.sendMessage(prefix + "警告チェストを作成しました");
                return true;
            } else if (args[0].equalsIgnoreCase("help")){
                help(sender);
                return true;
            }
            sender.sendMessage(prefix + "/hchest help");
            return true;
        }
        return false;
    }
    void help(CommandSender sender) {
        sender.sendMessage("§6===============§4[§5Man10HoneyChest§4]§6===============");
        sender.sendMessage("§a§l/mhchest <jail|warn>");
        sender.sendMessage("§6==============================================");
    }
    @EventHandler
    public void onClick(InventoryClickEvent e,Player p) {
        try {
            //警告用チェスト
            if (e.getView().getTitle().equals("§8チェスト")){
                if (e.getWhoClicked().hasPermission("man10honeychest.op")) {
                    return;
                }
                if(e.isShiftClick()||!(e.getRawSlot() < e.getInventory().getSize())) {
                    e.setCancelled(true);
                    return;
                }
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                e.setCancelled(true);
                String conStrComs = config.getString("warn.runCommand");
                String conStrSounds = config.getString("warn.playSound");

                Bukkit.broadcastMessage(prefix + config.getString("warn.runMessage").replaceAll("%player%", e.getWhoClicked().getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,conStrComs.replaceAll("%player%", e.getWhoClicked().getName()));
                String[] string = conStrSounds.split(",");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,"execute at " + e.getWhoClicked().getName() + " run playsound minecraft:" + string[0] + " master @a ~ ~ ~ " + string[1] + " " + string[2] + " 0");
                //できなかった →→→ p.playSound(p.getLocation(), string[0], string[1], string[2]);
                return;
            }
            //Jail用チェスト
            if (e.getView().getTitle().equals("§8Chest")){
                if (e.getWhoClicked().hasPermission("man10honeychest.op")) {
                    return;
                }
                if(e.isShiftClick()||!(e.getRawSlot() < e.getInventory().getSize())) {
                    e.setCancelled(true);
                    return;
                }
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                    e.setCancelled(true);
                String conStrComs = config.getString("jail.runCommand");
                String conStrSounds = config.getString("jail.playSound");

                Bukkit.broadcastMessage(prefix + config.getString("jail.runMessage").replaceAll("%player%", e.getWhoClicked().getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,conStrComs.replaceAll("%player%", e.getWhoClicked().getName()));
                String[] string = conStrSounds.split(",");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,"execute at " + e.getWhoClicked().getName() + " run playsound minecraft:" + string[0] + " master @a ~ ~ ~ " + string[1] + " " + string[2] + " 0");
                //できなかった →→→ p.playSound(p.getLocation(), string[0], string[1], string[2]);
                    return;
            }
        }catch (NullPointerException ee){
        }
    }
}
