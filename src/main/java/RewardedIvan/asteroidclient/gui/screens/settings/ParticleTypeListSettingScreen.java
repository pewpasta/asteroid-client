/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.screens.settings;

import RewardedIvan.asteroidclient.gui.widgets.WWidget;
import RewardedIvan.asteroidclient.utils.misc.Names;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.settings.Setting;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ParticleTypeListSettingScreen extends LeftRightListSettingScreen<ParticleType<?>> {
    public ParticleTypeListSettingScreen(GuiTheme theme, Setting<List<ParticleType<?>>> setting) {
        super(theme, "Select Particles", setting, setting.get(), Registry.PARTICLE_TYPE);
    }

    @Override
    protected WWidget getValueWidget(ParticleType<?> value) {
        return theme.label(getValueName(value));
    }

    @Override
    protected String getValueName(ParticleType<?> value) {
        return Names.get(value);
    }

    @Override
    protected boolean skipValue(ParticleType<?> value) {
        return !(value instanceof ParticleEffect);
    }
}
