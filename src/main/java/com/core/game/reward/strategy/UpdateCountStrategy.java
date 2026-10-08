package com.core.game.reward.strategy;

import org.jspecify.annotations.NullMarked;

@NullMarked
@FunctionalInterface
public interface UpdateCountStrategy
{
    /**
     * @return updated count
     */
    int update(int count);

}
