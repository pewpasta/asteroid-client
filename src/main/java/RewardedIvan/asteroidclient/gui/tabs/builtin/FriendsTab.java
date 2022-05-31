/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.gui.tabs.builtin;

import RewardedIvan.asteroidclient.gui.widgets.containers.WHorizontalList;
import RewardedIvan.asteroidclient.gui.widgets.containers.WSection;
import RewardedIvan.asteroidclient.gui.widgets.containers.WTable;
import RewardedIvan.asteroidclient.gui.widgets.input.WTextBox;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WMinus;
import RewardedIvan.asteroidclient.gui.widgets.pressable.WPlus;
import RewardedIvan.asteroidclient.utils.misc.NbtUtils;
import RewardedIvan.asteroidclient.gui.GuiTheme;
import RewardedIvan.asteroidclient.gui.tabs.Tab;
import RewardedIvan.asteroidclient.gui.tabs.TabScreen;
import RewardedIvan.asteroidclient.gui.tabs.WindowTabScreen;
import RewardedIvan.asteroidclient.settings.BoolSetting;
import RewardedIvan.asteroidclient.settings.ColorSetting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.settings.Settings;
import RewardedIvan.asteroidclient.systems.friends.Friend;
import RewardedIvan.asteroidclient.systems.friends.Friends;
import RewardedIvan.asteroidclient.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.Screen;

public class FriendsTab extends Tab {
    public FriendsTab() {
        super("Friends");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new FriendsScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof FriendsScreen;
    }

    private static class FriendsScreen extends WindowTabScreen {
        private final Settings settings = new Settings();

        public FriendsScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            SettingGroup sgGeneral = settings.getDefaultGroup();

            sgGeneral.add(new ColorSetting.Builder()
                    .name("color")
                    .description("The color used to show friends.")
                    .defaultValue(new SettingColor(0, 255, 180))
                    .onChanged(Friends.get().color::set)
                    .onModuleActivated(colorSetting -> colorSetting.set(Friends.get().color))
                    .build()
            );

            sgGeneral.add(new BoolSetting.Builder()
                    .name("attack")
                    .description("Whether to attack friends.")
                    .defaultValue(false)
                    .onChanged(aBoolean -> Friends.get().attack = aBoolean)
                    .onModuleActivated(booleanSetting -> booleanSetting.set(Friends.get().attack))
                    .build()
            );

            settings.onActivated();
        }

        @Override
        public void initWidgets() {
            // Settings
            add(theme.settings(settings)).expandX();

            // Friends
            WSection friends = add(theme.section("Friends")).expandX().widget();
            WTable table = friends.add(theme.table()).expandX().widget();

            initTable(table);

            // New
            WHorizontalList list = friends.add(theme.horizontalList()).expandX().widget();

            WTextBox nameW = list.add(theme.textBox("")).minWidth(400).expandX().widget();
            nameW.setFocused(true);

            WPlus add = list.add(theme.plus()).widget();
            add.action = () -> {
                String name = nameW.get().trim();

                if (Friends.get().add(new Friend(name))) {
                    nameW.set("");

                    table.clear();
                    initTable(table);
                }
            };

            enterAction = add.action;
        }

        private void initTable(WTable table) {
            for (Friend friend : Friends.get()) {
                table.add(theme.label(friend.name));

                WMinus remove = table.add(theme.minus()).expandCellX().right().widget();
                remove.action = () -> {
                    Friends.get().remove(friend);

                    table.clear();
                    initTable(table);
                };

                table.row();
            }
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(Friends.get());
        }

        @Override
        public boolean fromClipboard() {
            return NbtUtils.fromClipboard(Friends.get());
        }
    }
}
