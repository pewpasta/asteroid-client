/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.mixininterface;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public interface IEntityRenderer {
    Identifier getTextureInterface(Entity entity);
}
