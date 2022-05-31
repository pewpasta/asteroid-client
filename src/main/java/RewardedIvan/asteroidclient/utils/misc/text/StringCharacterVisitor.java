/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.utils.misc.text;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;

public class StringCharacterVisitor implements CharacterVisitor {

    public StringBuilder result = new StringBuilder();

    @Override
    public boolean accept(int index, Style style, int j) {
        result.append((char)j);
        return true;
    }
}
