package com.core.game.enemy.component.attribute;

import com.core.game.enemy.component.Component;
import lombok.RequiredArgsConstructor;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
@RequiredArgsConstructor
public class AttributeComponent<T extends Attributable & LivingEntity> implements Component<T>
{

    protected final double value;
    protected final Attribute attribute;

    @Override
    public void applyComponent(T entity)
    {
        applyAttribute(entity);
    }

    protected final void applyAttribute(Attributable entity)
    {
        entity.getAttribute(attribute);
        Objects.requireNonNull(entity.getAttribute(attribute)).setBaseValue(value);
    }

}
