/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.events.entity.player;

import net.minecraft.fluid.FluidState;

public class CanWalkOnFluidEvent {
    private static final CanWalkOnFluidEvent INSTANCE = new CanWalkOnFluidEvent();

    public FluidState fluidState;
    public boolean walkOnFluid;

    public static CanWalkOnFluidEvent get(FluidState fluid) {
        INSTANCE.fluidState = fluid;
        INSTANCE.walkOnFluid = false;
        return INSTANCE;
    }
}
