package com.core.game.reward.strategy;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;

@NullMarked
@RequiredArgsConstructor
public class LinearUpdateStrategy implements UpdateCountStrategy
{

    private final int coefficient;

    @Override
    public int update(int count)
    {
        return count + coefficient;
    }

}
