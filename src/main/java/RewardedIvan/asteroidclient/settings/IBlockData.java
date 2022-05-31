/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.settings;

import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.WidgetScreen;
import RewardedIvan.asteroidclient.utils.misc.IChangeable;
import RewardedIvan.asteroidclient.utils.misc.ICopyable;
import RewardedIvan.asteroidclient.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
