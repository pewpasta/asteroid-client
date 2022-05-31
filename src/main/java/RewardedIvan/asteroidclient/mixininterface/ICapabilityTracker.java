/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.mixininterface;

public interface ICapabilityTracker {
    boolean get();

    void set(boolean state);
}
