package com.core.game;

import com.core.game.reward.NumerousItemsReward;
import com.core.game.reward.strategy.LinearUpdateStrategy;
import com.core.game.reward.strategy.UpdateCountStrategy;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
public final class GameController
{

    private static final UpdateCountStrategy REWARD_UPDATE_STRATEGY = new LinearUpdateStrategy(2);

    private final NumerousItemsReward reward;
    private final GameArena arena;
    private final Player player;
    private final GameMode mode;
    private final WaveBar bar;

    private int currentWave = 0, aliveEntities = 0;

    public GameController(Player player) throws IOException
    {
        this.arena = new GameArena(player.getUniqueId().toString());
        this.bar = new WaveBar(player);
        this.player = player;
        // for testing mechanics
        this.mode = GameMode.EASY;
        this.reward = new NumerousItemsReward(ItemStack.of(Material.GOLD_INGOT), REWARD_UPDATE_STRATEGY);
    }

    @NullMarked
    @RequiredArgsConstructor
    private enum GameTitle
    {
        START(Component.text("Игра началась!")),
        END(Component.text("Игра окончена!"))
        ;

        private final TextComponent text;

        public void show(Player player)
        {
            player.sendTitlePart(TitlePart.TITLE, text);
        }

        public static void show(Player player, String text)
        {
            player.sendTitlePart(TitlePart.TITLE, Component.text(text));
        }
    }

    /**
     * @return boolean - was a wave launched
     */
    private boolean startWave(int index)
    {
        GameMode.Wave wave = mode.getWave(index);
        if (wave != null)
        {
            GameTitle.show(player, "Волна " + index + " началась!");
            bar.updateState(index, aliveEntities = wave.entityCount());
            arena.spawnEntities(wave.enemies());
            reward.update();
            return true;
        }
        return false;
    }

    public void start()
    {
        player.teleport(arena.getPlayerSpawn());
        GameTitle.START.show(player);
        startWave(0);
    }

    public void stop()
    {
        bar.clear();
        GameLobby.teleportPlayer(player);
        reward.giveReward(player);
        GameTitle.END.show(player);
        arena.delete(false);
    }

    /**
     * @return boolean - was new wave launched
     */
    public boolean monsterDeath()
    {
        bar.updateProgress(aliveEntities -= 1);
        return aliveEntities == 0 && !startWave(currentWave += 1);
    }

}
