package RewardedIvan.asteroidclient.systems.hud.modules;

import RewardedIvan.asteroidclient.renderer.GL;
import RewardedIvan.asteroidclient.renderer.Renderer2D;
import RewardedIvan.asteroidclient.settings.DoubleSetting;
import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.systems.hud.HUD;
import RewardedIvan.asteroidclient.systems.hud.HudRenderer;
import net.minecraft.util.Identifier;

public class LogoHud extends HudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The scale of the logo.")
        .defaultValue(3)
        .min(0.1)
        .sliderRange(0.1, 10)
        .build()
    );

    private final Identifier TEXTURE = new Identifier("asteroid-client", "textures/asteroid.png");

    public LogoHud(HUD hud) {
        super(hud, "logo", "Shows the Asteroid logo in the HUD.");
    }

    @Override
    public void update(HudRenderer renderer) {
        box.setSize(64 * scale.get(), 64 * scale.get());
    }

    @Override
    public void render(HudRenderer renderer) {
        GL.bindTexture(TEXTURE);
        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texQuad(box.getX(), box.getY(), box.width, box.height, Utils.WHITE);
        Renderer2D.TEXTURE.render(null);
    }
}
