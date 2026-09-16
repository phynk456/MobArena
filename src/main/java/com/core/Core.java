package com.core;

import com.core.command.GameControl;
import com.core.listener.EventListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.jspecify.annotations.NonNull;

import java.util.logging.Logger;

public final class Core extends JavaPlugin
{

    private static @Getter Logger loggers;
    private static @Getter FileConfiguration configuration;
    private static @Getter Plugin instance;

    @Override
    public void onEnable()
    {
        updateInstance(this);
        loggers.info("Plugin has been enabled!");
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        registerCommand("game", new GameControl());
    }

    @Override
    public void onDisable()
    {
        Bukkit.getWorlds().forEach(world -> Bukkit.unloadWorld(world, true));
    }

    public static void updateInstance(@NonNull Plugin plugin)
    {
        instance = plugin;
        loggers = plugin.getLogger();
        configuration = plugin.getConfig();
        plugin.saveDefaultConfig();
    }

    public static @NonNull Server getServerFromInstance()
    {
        return getInstance().getServer();
    }

}
