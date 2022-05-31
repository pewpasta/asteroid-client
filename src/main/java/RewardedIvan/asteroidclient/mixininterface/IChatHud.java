/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.mixininterface;

import net.minecraft.text.Text;

public interface IChatHud {
    void add(Text message, int messageId, int timestamp, boolean refresh);
}
