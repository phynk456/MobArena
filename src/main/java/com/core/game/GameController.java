package com.core.game;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;

@NullMarked
public final class GameController
{

    private final GameDifficulty mode;
    private final GameArena arena;
    private final Player player;
    private final WaveBar bar;

    private int currentWave = 0, aliveEntities = 0;

    public GameController(Player player, GameDifficulty mode) throws IOException
    {
        this.arena = new GameArena(player.getUniqueId().toString());
        this.bar = new WaveBar(player);
        this.player = player;
        this.mode = mode;
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
        GameDifficulty.Wave wave = mode.getWave(index);
        if (wave != null)
        {
            GameTitle.show(player, "Волна " + index + " началась!");
            bar.updateState(index, aliveEntities = wave.entityCount());
            arena.spawnEntities(wave.enemies());
            mode.getReward().update();
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
        mode.getReward().giveReward(player);
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
