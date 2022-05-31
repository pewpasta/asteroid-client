package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.systems.hud.HUD;

public class WatermarkHud extends DoubleTextHudElement {
    public WatermarkHud(HUD hud) {
        super(hud, "watermark", "Displays a Asteroid Client watermark.", "Asteroid Client ");
    }

    @Override
    protected String getRight() {
        if (AsteroidClient.DEV_BUILD.isEmpty()) {
            return AsteroidClient.VERSION.toString();
        }

        return AsteroidClient.VERSION + " " + AsteroidClient.DEV_BUILD;
    }
}
