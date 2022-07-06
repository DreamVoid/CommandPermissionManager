package me.dreamvoid.commandpermissionmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class main extends JavaPlugin {
    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        reloadConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                for(String s : getConfig().getStringList("commands")){
                    String[] c = s.split(":");
                    if(c.length != 2) {
                        getLogger().warning("Format error found in config: " + s);
                        continue;
                    }
                    try {
                        Objects.requireNonNull(Bukkit.getPluginCommand(c[0])).setPermission(c[1]);
                        getLogger().info("Set command " + c[0] + "'s permission to " + c[1]);
                    } catch (NullPointerException e){
                        getLogger().warning("Command \""+c[0]+"\" doesn't exist!");
                    }
                }

                for(String s : getConfig().getStringList("commands-with-plugin")){
                    String[] c = s.split(":");
                    if(c.length != 2) {
                        getLogger().warning("Format error found in config: " + s);
                        continue;
                    }
                    try {
                        Plugin p = Objects.requireNonNull(Bukkit.getPluginCommand(c[0])).getPlugin();

                        for(String k : p.getDescription().getCommands().keySet()){
                            Bukkit.getPluginCommand(k).setPermission(c[1]);
                            getLogger().info("Set command " + k + "'s permission to " + c[1]);
                        }
                        getLogger().info("Set all of " + p.getName() + "'s commands permission to " + c[1]);
                    } catch (NullPointerException e){
                        getLogger().warning("Command or plugin \""+c[0]+"\" doesn't exist!");
                    }
                }
            }
        }.runTask(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("reload")){
                onEnable();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.reload","&eConfig reload, but it recommended to restart server if you delete some commands.")));
            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.help","&eTry /commandpermissionmanager reload")));
        } else sender.sendMessage("This server is running "+ getDescription().getName() +" version "+ getDescription().getVersion()+" by "+ getDescription().getAuthors().toString().replace("[","").replace("]",""));
        return true;
    }
}
