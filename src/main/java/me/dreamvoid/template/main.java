package me.dreamvoid.template;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        //saveDefaultConfig(); // 加载配置文件
        //getCommand("some-command").setExecutor(this); // 注册插件命令
        //Bukkit.getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return super.onCommand(sender, command, label, args);
    }
}
