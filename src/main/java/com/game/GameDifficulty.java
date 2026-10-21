package com.game;

import com.game.enemy.EnemyType;
import com.game.reward.ItemReward;
import com.game.reward.strategy.LinearUpdateStrategy;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static com.game.enemy.EnemyType.*;

@NullMarked
public enum GameDifficulty
{

    // for testing mechanics
    EASY(
        new ItemReward.NumerousItemsReward(ItemStack.of(Material.GOLD_INGOT),  new LinearUpdateStrategy(2)),
        Wave.of(ZOMBIE_ADULT, ZOMBIE_BABY),
        Wave.of(ZOMBIE_ADULT, ZOMBIE_BABY)
    );

    private final List<Wave> waves;
    @Getter private final int waveCount;
    @Getter private final ItemReward.NumerousItemsReward reward;

    GameDifficulty(ItemReward.NumerousItemsReward reward, Wave... waves)
    {
        this.reward = reward;
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
        public static Wave of(EnemyType... enemies)
        {
            return new Wave(List.of(enemies), enemies.length);
        }

    }

}

