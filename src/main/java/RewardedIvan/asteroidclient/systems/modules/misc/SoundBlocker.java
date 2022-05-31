/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.modules.misc;

import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.settings.SoundEventListSetting;
import RewardedIvan.asteroidclient.events.world.PlaySoundEvent;
import RewardedIvan.asteroidclient.systems.modules.Categories;
import RewardedIvan.asteroidclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.sound.SoundEvent;

import java.util.List;

public class SoundBlocker extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<SoundEvent>> sounds = sgGeneral.add(new SoundEventListSetting.Builder()
        .name("sounds")
        .description("Sounds to block.")
        .build()
    );

    public SoundBlocker() {
        super(Categories.Misc, "sound-blocker", "Cancels out selected sounds.");
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        for (SoundEvent sound : sounds.get()) {
            if (sound.getId().equals(event.sound.getId())) {
                event.cancel();
                break;
            }
        }
    }
}
