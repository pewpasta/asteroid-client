/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.utils.world.TickRate;
import RewardedIvan.asteroidclient.systems.hud.HUD;

public class TpsHud extends DoubleTextHudElement {
    public TpsHud(HUD hud) {
        super(hud, "tps", "Displays the server's TPS.", "TPS: ");
    }

    @Override
    protected String getRight() {
        return String.format("%.1f", TickRate.INSTANCE.getTickRate());
    }
}
