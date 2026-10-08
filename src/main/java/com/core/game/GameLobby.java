package com.core.game;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@NullMarked
// Singleton
public final class GameLobby
{

    @Nullable private static Location spawn;
    @Nullable private static UUID id;

    private GameLobby()
    {
        throw new AssertionError();
    }

    public static void updateSpawn(World world, double x, double y, double z, float yaw, float pitch)
    {
        spawn = new Location(world, x, y, z, yaw, pitch);
        id = world.getUID();
    }

    public static void teleportPlayer(Player player)
    {
        if (player.isOnline())
        {
            if (player.isDead())
            {
                player.setRespawnLocation(spawn, true);
            }
            else
            {
                if (spawn == null)
                {
                    throw new IllegalStateException();
                }
                player.teleport(spawn);
            }
        }
    }

    public static boolean inLobby(Player player)
    {
        return player.getWorld().getUID().equals(id);
    }

}
