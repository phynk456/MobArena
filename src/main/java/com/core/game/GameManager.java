package com.core.game;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NullMarked
// Singleton
public final class GameManager
{

    private GameManager()
    {
        throw new AssertionError();
    }

    private static final Map<UUID, GameController> CONTROLLER_MAP = new HashMap<>();

    public static void start(Player player) throws IOException
    {
        if (!GameLobby.inLobby(player))
        {
            return;
        }

        GameController controller = new GameController(player, GameDifficulty.EASY);
        controller.start();
        CONTROLLER_MAP.put(player.getWorld().getUID(), controller);
    }

    public static void stop(UUID id)
    {
        if (CONTROLLER_MAP.containsKey(id))
        {
            CONTROLLER_MAP.remove(id).stop();
        }
    }

    public static void monsterDeath(UUID id)
    {
        if (CONTROLLER_MAP.containsKey(id) && CONTROLLER_MAP.get(id).monsterDeath())
        {
            stop(id);
        }
    }

}
