package com.core.listener;

import com.core.game.GameManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public class EventListener implements Listener
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

}