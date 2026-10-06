package com.core.game.reward;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Reward
{

    void giveReward(Player player);

}
