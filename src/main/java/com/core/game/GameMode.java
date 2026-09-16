package com.core.game;

import com.core.game.enemy.EnemyType;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static com.core.game.enemy.EnemyType.*;

public enum GameMode
{

    // just test
    EASY(
        Wave.of(ZOMBIE_ADULT, ZOMBIE_BABY),
        Wave.of(ZOMBIE_ADULT, ZOMBIE_BABY)
    ),
    NORMAL,
    HARD;

    private final List<Wave> waves;
    @Getter private final int waveCount;

    GameMode(Wave... waves)
    {
        this.waves = List.of(waves);
        this.waveCount = waves.length - 1;
    }

     public @Nullable Wave getWave(int index)
     {
         return index >= waves.size() ? null : waves.get(index);
     }

    public record Wave(List<EnemyType> enemies, int entityCount)
    {

        @Contract("_ -> new")
        public static @NonNull Wave of(EnemyType... enemies)
        {
            return new Wave(List.of(enemies), enemies.length);
        }

    }

}

