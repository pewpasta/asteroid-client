/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.events.entity;

import net.minecraft.entity.Entity;

public class EntityRemovedEvent {
    private static final EntityRemovedEvent INSTANCE = new EntityRemovedEvent();

    public Entity entity;

    public static EntityRemovedEvent get(Entity entity) {
        INSTANCE.entity = entity;
        return INSTANCE;
    }
}
