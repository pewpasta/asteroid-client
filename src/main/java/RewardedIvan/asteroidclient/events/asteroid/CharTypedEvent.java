/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.events.asteroid;

import RewardedIvan.asteroidclient.events.Cancellable;

public class CharTypedEvent extends Cancellable {
    private static final CharTypedEvent INSTANCE = new CharTypedEvent();

    public char c;

    public static CharTypedEvent get(char c) {
        INSTANCE.setCancelled(false);
        INSTANCE.c = c;
        return INSTANCE;
    }
}
