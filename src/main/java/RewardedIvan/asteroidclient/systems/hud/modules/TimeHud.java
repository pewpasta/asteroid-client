/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.settings.EnumSetting;
import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.systems.hud.HUD;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimeHud extends DoubleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Type> timeType = sgGeneral.add(new EnumSetting.Builder<Type>()
        .name("type")
        .description("Which time to use.")
        .defaultValue(Type.Game)
        .build()
    );

    public TimeHud(HUD hud) {
        super(hud, "time", "Displays the world time.", "Time: ");
    }

    @Override
    protected String getRight() {
        return switch (timeType.get()) {
            case Game -> isInEditor() ? "00:00" : Utils.getWorldTime();
            case Local -> LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        };
    }

    public enum Type {
        Local,
        Game
    }
}
