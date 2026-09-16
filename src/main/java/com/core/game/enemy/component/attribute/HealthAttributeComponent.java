package com.core.game.enemy.component.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class HealthAttributeComponent extends AttributeComponent<LivingEntity>
{

    public HealthAttributeComponent(double value)
    {
        super(value, Attribute.MAX_HEALTH);
    }

    @Override
    public void applyComponent(LivingEntity entity)
    {
        applyAttribute(entity);
        entity.setHealth(value);
    }

}
