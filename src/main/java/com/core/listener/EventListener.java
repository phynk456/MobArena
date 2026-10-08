package com.core.listener;

import com.core.Core;
import com.core.game.GameLobby;
import com.core.game.GameManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.UUID;

@NullMarked
public final class EventListener implements Listener
{

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        UUID id = entity.getWorld().getUID();
        if (entity instanceof Player)
        {
            GameManager.stop(id);
        }
        else
        {
            GameManager.monsterDeath(id);
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        GameManager.stop(event.getPlayer().getWorld().getUID());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        GameLobby.teleportPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws IOException
    {
        if (event.getHand() == EquipmentSlot.HAND && event.getAction().isRightClick())
        {
            Block block = event.getClickedBlock();
            if (block != null && block.getType().equals(Material.BARRIER))
            {
                GameManager.start(event.getPlayer());
            }
        }
    }

}