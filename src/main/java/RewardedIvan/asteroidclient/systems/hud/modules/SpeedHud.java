/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.systems.hud.HUD;

public class SpeedHud extends DoubleTextHudElement {
    public SpeedHud(HUD hud) {
        super(hud, "speed", "Displays your horizontal speed.", "Speed: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "0";

        return String.format("%.1f", Utils.getPlayerSpeed());
    }
}
