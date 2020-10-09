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
import org.bukkit.scheduler.BukkitRunnable;

public final class Man10HoneyChest extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();
    String prefix = "§6§l[§a§lM§f§lan§d§l10§6§lHChest]§r";

    public String jailrunMessage ="";
    public String jailrunCommand1 ="";
    public String jailrunCommand2 ="";
    public String jailrunCommand3 ="";
    public String jailplaySound ="";
    public String warnrunMessage ="";
    public String warnrunCommand1 ="";
    public String warnrunCommand2 ="";
    public String warnrunCommand3 ="";
    public String warnplaySound ="";
    MySQLManager mysql = new MySQLManager(this,"MHoneyChest");

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Man10HoneyChest起動しました");
        Bukkit.getPluginManager().registerEvents(this,this);
        saveDefaultConfig();
        config = getConfig();
        jailrunMessage = config.getString("jail.runMessage");
        jailrunCommand1 = config.getString("jail.runCommand1");
        jailrunCommand2 = config.getString("jail.runCommand2");
        jailrunCommand3 = config.getString("jail.runCommand3");
        jailplaySound = config.getString("jail.playSound");
        warnrunMessage  = config.getString("warn.runMessage");
        warnrunCommand1 = config.getString("warn.runCommand1");
        warnrunCommand2 = config.getString("warn.runCommand2");
        warnrunCommand3 = config.getString("warn.runCommand3");
        warnplaySound = config.getString("warn.playSound");
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
                sender.sendMessage(prefix + "/hchest help");
                return true;
            }
            if (args.length >= 2) {
                sender.sendMessage(prefix + "/hchest help");
                return true;
            }
            if (args[0].equalsIgnoreCase("jail")) {
                Player p = (Player) sender;
                ItemStack honeyChest = new ItemStack(Material.CHEST);
                ItemMeta meta = honeyChest.getItemMeta();
                meta.setDisplayName("§Ｈ§ｏ§ｎ§ｅ§ｙ§Ｃ§ｈ§ｅ§ｓ§ｔChest");
                honeyChest.setItemMeta(meta);
                p.getInventory().addItem(honeyChest);
                sender.sendMessage(prefix + "ジェイルチェストを作成しました");
                return true;
            }
            if (args[0].equalsIgnoreCase("warn")) {
                Player p = (Player) sender;
                ItemStack honeyChest = new ItemStack(Material.CHEST);
                ItemMeta meta = honeyChest.getItemMeta();
                meta.setDisplayName("§Ｈ§ｏ§ｎ§ｅ§ｙChest");
                honeyChest.setItemMeta(meta);
                p.getInventory().addItem(honeyChest);
                sender.sendMessage(prefix + "警告チェストを作成しました");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                config = getConfig();
                jailrunMessage = config.getString("jail.runMessage");
                jailrunCommand1 = config.getString("jail.runCommand1");
                jailrunCommand2 = config.getString("jail.runCommand2");
                jailrunCommand3 = config.getString("jail.runCommand3");
                jailplaySound = config.getString("jail.playSound");
                warnrunMessage  = config.getString("warn.runMessage");
                warnrunCommand1 = config.getString("warn.runCommand1");
                warnrunCommand2 = config.getString("warn.runCommand2");
                warnrunCommand3 = config.getString("warn.runCommand3");
                warnplaySound = config.getString("warn.playSound");
                sender.sendMessage(prefix + "§econfigのリロードができました");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")){
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
        sender.sendMessage("§a§l</mhchest jail> §e§lJail専用チェストを出します");
        sender.sendMessage("§a§l</mhchest warn> §e§l警告専用チェストを出します");
        sender.sendMessage("§a§l</mhchest reload> §e§lconfigファイルをリロードします");
        sender.sendMessage("§6==============================================");
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
            //警告用チェスト
        if (e.getView().getTitle().equals("§Ｈ§ｏ§ｎ§ｅ§ｙChest")){
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
            String conStrComs1 = config.getString("warn.runCommand1");
            String conStrComs2 = config.getString("warn.runCommand2");
            String conStrComs3 = config.getString("warn.runCommand3");
            String conStrSounds = config.getString("warn.playSound");

            Bukkit.broadcastMessage(prefix + config.getString("warn.runMessage").replaceAll("%player%", e.getWhoClicked().getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,conStrComs1.replaceAll("%player%", e.getWhoClicked().getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,conStrComs2.replaceAll("%player%", e.getWhoClicked().getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,conStrComs3.replaceAll("%player%", e.getWhoClicked().getName()));
            String[] string = conStrSounds.split(",");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender() ,"execute at " + e.getWhoClicked().getName() + " run playsound minecraft:" + string[0] + " master @a ~ ~ ~ " + string[1] + " " + string[2] + " 0");
            //できなかった →→→ p.playSound(p.getLocation(), string[0], string[1], string[2]);
            return;
        }
        //Jail用チェスト
        if (e.getView().getTitle().equals("§Ｈ§ｏ§ｎ§ｅ§ｙ§Ｃ§ｈ§ｅ§ｓ§ｔChest")) {
            if (e.getWhoClicked().hasPermission("man10honeychest.op")) {
                return;
            }
            if (e.isShiftClick() || !(e.getRawSlot() < e.getInventory().getSize())) {
                e.setCancelled(true);
                return;
            }
            if (e.getClick().isKeyboardClick()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            e.setCancelled(true);
            String conStrComs1 = config.getString("jail.runCommand1");
            String conStrComs2 = config.getString("jail.runCommand2");
            String conStrComs3 = config.getString("jail.runCommand3");
            String conStrSounds = config.getString("jail.playSound");

            Bukkit.broadcastMessage(prefix + config.getString("jail.runMessage").replaceAll("%player%", e.getWhoClicked().getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), conStrComs1.replaceAll("%player%", e.getWhoClicked().getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), conStrComs2.replaceAll("%player%", e.getWhoClicked().getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), conStrComs3.replaceAll("%player%", e.getWhoClicked().getName()));
            String[] string = conStrSounds.split(",");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + e.getWhoClicked().getName() + " run playsound minecraft:" + string[0] + " master @a ~ ~ ~ " + string[1] + " " + string[2] + " 0");
            //できなかった →→→ p.playSound(p.getLocation(), string[0], string[1], string[2]);
            MySQLProcess mysql = new MySQLProcess((Player)e.getWhoClicked(),this);
            mysql.start();
            return;
        }
    }
}
class MySQLProcess extends Thread {
    Player p;
    Man10HoneyChest pl;
    public MySQLProcess(Player p, Man10HoneyChest pl){
        this.pl = pl;
        this.p = p;
    }
    public void run(){
        MySQLManager mysql = new MySQLManager(pl, "Man10HoneyChest");
        String loc = p.getLocation().getWorld().getName() + " ` " + p.getLocation().getX() + " ` " + p.getLocation().getY() + " ` " + p.getLocation().getZ();
        mysql.execute("INSERT INTO man10honeychest_log (mcid,uuid,location) values ('" + p.getName() + "','" + p.getUniqueId() + "','" + loc + "');");
        mysql.close();
    }
}