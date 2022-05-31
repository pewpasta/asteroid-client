/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.utils.misc.HorizontalDirection;
import RewardedIvan.asteroidclient.systems.hud.HUD;

// Credits to snail for helping, Finished in 3 am ez. This was in total 20 hours of work. subscribe to pewdipie. Meteor on top ez
// Gilded moment ez
// Gilded did monky code so I fixed :coool: - MineGame159
// i stole your code - RewardedIvan

public class RotationHud extends DoubleTextHudElement {
    public RotationHud(HUD hud) {
        super(hud, "rotation", "Displays your rotation.", "");
    }

    @Override
    protected String getRight() {
        float yaw = mc.gameRenderer.getCamera().getYaw() % 360;
        if (yaw < 0) yaw += 360;
        if (yaw > 180) yaw -= 360;

        float pitch = mc.gameRenderer.getCamera().getPitch() % 360;
        if (pitch < 0) pitch += 360;
        if (pitch > 180) pitch -= 360;

        HorizontalDirection dir = HorizontalDirection.get(mc.gameRenderer.getCamera().getYaw());
        setLeft(String.format("%s %s ", dir.name, dir.axis));

        return String.format("(%.1f, %.1f)", yaw, pitch);
    }
}
