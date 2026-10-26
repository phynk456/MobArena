package com.game;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class WaveBar
{

    private final BossBar bar = BossBar.bossBar(Component.text("Волна 0"), 0, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
    private final Player player;
    private float entityCoefficient = 0;

    WaveBar(Player player)
    {
        this.player = player;
        bar.addViewer(player);
    }

    void clear()
    {
        bar.removeViewer(player);
    }

    void updateState(int wave, int entityCount)
    {
        entityCoefficient = 1f / entityCount;
        bar.name(Component.text("Волна " + wave));
        bar.progress(1);
    }

    void updateProgress(int aliveEntities)
    {
        bar.progress(entityCoefficient * aliveEntities);
    }

}
