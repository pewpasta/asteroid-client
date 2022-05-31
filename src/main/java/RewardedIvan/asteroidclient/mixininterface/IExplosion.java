/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.mixininterface;

import net.minecraft.util.math.Vec3d;

public interface IExplosion {
    void set(Vec3d pos, float power, boolean createFire);
}
