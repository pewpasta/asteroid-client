package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.settings.ColorSetting;
import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import RewardedIvan.asteroidclient.utils.render.color.SettingColor;

public class WelcomeHud extends DoubleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
        .name("color")
        .description("Color of welcome text.")
        .defaultValue(new SettingColor(140, 140, 140))
        .build()
    );

    public WelcomeHud(HUD hud) {
        super(hud, "welcome", "Displays a welcome message.", "Welcome to Asteroid Client, ");
        rightColor = color.get();
    }

    @Override
    protected String getRight() {
        return mc.getSession().getUsername() + "!";
    }
}
