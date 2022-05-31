/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.utils.misc;

public interface ICopyable<T extends ICopyable<T>> {
    T set(T value);

    T copy();
}
