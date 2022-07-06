package me.dreamvoid.commandpermissionmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class main extends JavaPlugin {
    private static final ArrayList<String> commands = new ArrayList<>();

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
                initial();
            }
        }.runTask(this);
        if(getConfig().getBoolean("bStats",true)) new Metrics(this, 15686);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("reload")){
                reloadConfig();
                initial();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.reload","&eConfig reload, but it recommended to restart server if you delete some commands.")));
            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.help","&eTry /commandpermissionmanager reload")));
        } else sender.sendMessage("This server is running "+ getDescription().getName() +" version "+ getDescription().getVersion()+" by "+ getDescription().getAuthors().toString().replace("[","").replace("]",""));
        return true;
    }

    private void initial(){
        for(String s : getConfig().getStringList("commands")){
            String[] c = s.split(":");
            if(c.length != 2) {
                getLogger().warning("Format error found in config: " + s);
                continue;
            }
            try {
                PluginCommand cmd = Bukkit.getPluginCommand(c[0]);
                cmd.setPermission(c[1]);
                if(getConfig().getBoolean("make-list-unknown-command", true)) cmd.setPermissionMessage("Unknown command. Type \"/help\" for help.");
                commands.add(c[0]);
                getLogger().info(String.format("Set permission of command \"%s\" to \"%s\"", c[0], c[1]));
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
                Plugin p = Bukkit.getPluginCommand(c[0]).getPlugin();
                Set<String> keySet = p.getDescription().getCommands().keySet();
                for (String k : keySet) {
                    if(!commands.contains(k)) {
                        PluginCommand cmd = Bukkit.getPluginCommand(k);
                        cmd.setPermission(c[1]);
                        if(getConfig().getBoolean("make-list-unknown-command", true)) cmd.setPermissionMessage("Unknown command. Type \"/help\" for help.");
                    } else {
                        getLogger().info("Skip changed command " + k);
                    }
                }
                getLogger().info("Set permissions for "+ keySet.size() +" commands of plugin \"" + p.getName() + "\" to \"" + c[1] + "\"");

            } catch (NullPointerException e){
                getLogger().warning("Command or plugin \""+c[0]+"\" doesn't exist!");
            }
        }
        commands.clear();
    }
}
