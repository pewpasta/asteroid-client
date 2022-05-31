/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.events.world;

import RewardedIvan.asteroidclient.events.Cancellable;
import net.minecraft.client.sound.SoundInstance;

public class PlaySoundEvent extends Cancellable {
    private static final PlaySoundEvent INSTANCE = new PlaySoundEvent();

    public SoundInstance sound;

    public static PlaySoundEvent get(SoundInstance sound) {
        INSTANCE.setCancelled(false);
        INSTANCE.sound = sound;
        return INSTANCE;
    }
}
