/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.events.asteroid;

import RewardedIvan.asteroidclient.systems.modules.Module;

public class ModuleBindChangedEvent {
    private static final ModuleBindChangedEvent INSTANCE = new ModuleBindChangedEvent();

    public Module module;

    public static ModuleBindChangedEvent get(Module module) {
        INSTANCE.module = module;
        return INSTANCE;
    }
}
