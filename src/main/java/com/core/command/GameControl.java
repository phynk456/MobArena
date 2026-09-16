package com.core.command;

import com.core.game.GameManager;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

/**
* This is a temporary class for testing mechanics
*/
@NullMarked
public class GameControl implements BasicCommand
{

    @Override
    public void execute(CommandSourceStack css, String[] args)
    {
        if (css.getSender() instanceof Player plr)
        {
            if (args[0].equals("start"))
            {
                try
                {
                    GameManager.start(plr);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                GameManager.stop(css.getLocation().getWorld().getUID());
            }
        }
    }

}
