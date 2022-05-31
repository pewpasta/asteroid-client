package RewardedIvan.asteroidclient.gui.tabs;

import RewardedIvan.asteroidclient.gui.tabs.builtin.*;
import RewardedIvan.asteroidclient.utils.Init;
import RewardedIvan.asteroidclient.utils.InitStage;

import java.util.ArrayList;
import java.util.List;

public class Tabs {
    private static final List<Tab> tabs = new ArrayList<>();

    @Init(stage = InitStage.Pre)
    public static void init() {
        add(new ModulesTab());
        add(new ConfigTab());
        add(new GuiTab());
        add(new HudTab());
        add(new FriendsTab());
        add(new ProfilesTab());
    }

    public static void add(Tab tab) {
        tabs.add(tab);
    }

    public static List<Tab> get() {
        return tabs;
    }
}
