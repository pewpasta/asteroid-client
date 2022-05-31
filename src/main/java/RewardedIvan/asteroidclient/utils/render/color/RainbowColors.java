/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.utils.render.color;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.events.world.TickEvent;
import RewardedIvan.asteroidclient.gui.GuiThemes;
import RewardedIvan.asteroidclient.gui.WidgetScreen;
import RewardedIvan.asteroidclient.settings.ColorSetting;
import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.systems.config.Config;
import RewardedIvan.asteroidclient.systems.waypoints.Waypoint;
import RewardedIvan.asteroidclient.systems.waypoints.Waypoints;
import RewardedIvan.asteroidclient.utils.Init;
import RewardedIvan.asteroidclient.utils.InitStage;
import RewardedIvan.asteroidclient.utils.misc.UnorderedArrayList;
import meteordevelopment.orbit.EventHandler;

import java.util.List;

public class RainbowColors {
    private static final List<Setting<SettingColor>> colorSettings = new UnorderedArrayList<>();
    private static final List<SettingColor> colors = new UnorderedArrayList<>();
    private static final List<Runnable> listeners = new UnorderedArrayList<>();

    public static final RainbowColor GLOBAL = new RainbowColor();

    @Init(stage = InitStage.Post)
    public static void init() {
        AsteroidClient.EVENT_BUS.subscribe(RainbowColors.class);
    }

    public static void addSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    public static void removeSetting(Setting<SettingColor> setting) {
        colorSettings.remove(setting);
    }

    public static void add(SettingColor color) {
        colors.add(color);
    }

    public static void register(Runnable runnable) {
        listeners.add(runnable);
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        GLOBAL.setSpeed(Config.get().rainbowSpeed.get() / 100);
        GLOBAL.getNext();

        for (Setting<SettingColor> setting : colorSettings) {
            if (setting.module == null || setting.module.isActive()) setting.get().update();
        }

        for (SettingColor color : colors) {
            color.update();
        }

        for (Waypoint waypoint : Waypoints.get()) {
            waypoint.color.update();
        }

        if (AsteroidClient.mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup group : GuiThemes.get().settings) {
                for (Setting<?> setting : group) {
                    if (setting instanceof ColorSetting) ((SettingColor) setting.get()).update();
                }
            }
        }

        for (Runnable listener : listeners) listener.run();
    }
}
