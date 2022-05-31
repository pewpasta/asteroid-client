/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.events.world;

public class ConnectToServerEvent {
    private static final ConnectToServerEvent INSTANCE = new ConnectToServerEvent();

    public static ConnectToServerEvent get() {
        return INSTANCE;
    }
}
