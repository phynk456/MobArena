package com.core.game;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class WaveBar
{

    private final BossBar bar = BossBar.bossBar(Component.text("Волна 0"), 0, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
    private final Player player;
    private float entityCoefficient = 0;

    public WaveBar(Player player)
    {
        this.player = player;
        bar.addViewer(player);
    }

    public void clear()
    {
        bar.removeViewer(player);
    }

    public void updateState(int wave, int entityCount)
    {
        entityCoefficient = 1f / entityCount;
        bar.name(Component.text("Волна " + wave));
        bar.progress(1);
    }

    public void updateProgress(int aliveEntities)
    {
        bar.progress(entityCoefficient * aliveEntities);
    }

}
