package RewardedIvan.asteroidclient.systems.hud;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.gui.screens.HudEditorScreen;
import RewardedIvan.asteroidclient.gui.screens.HudElementScreen;
import RewardedIvan.asteroidclient.settings.*;
import RewardedIvan.asteroidclient.systems.hud.modules.*;
import RewardedIvan.asteroidclient.utils.misc.Keybind;
import RewardedIvan.asteroidclient.utils.misc.NbtUtils;
import RewardedIvan.asteroidclient.utils.render.AlignmentY;
import RewardedIvan.asteroidclient.events.render.Render2DEvent;
import RewardedIvan.asteroidclient.systems.System;
import RewardedIvan.asteroidclient.systems.Systems;
import RewardedIvan.asteroidclient.utils.render.AlignmentX;
import RewardedIvan.asteroidclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HUD extends System<HUD> {
    public final Settings settings = new Settings();

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgEditor = settings.createGroup("Editor");

    public boolean active;

    // General

    public final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("Scale of the HUD.")
        .defaultValue(1)
        .min(0.75)
        .sliderRange(0.75, 4)
        .build()
    );

    public final Setting<SettingColor> primaryColor = sgGeneral.add(new ColorSetting.Builder()
        .name("primary-color")
        .description("Primary color of text.")
        .defaultValue(new SettingColor(255, 255, 255))
        .build()
    );

    public final Setting<SettingColor> secondaryColor = sgGeneral.add(new ColorSetting.Builder()
        .name("secondary-color")
        .description("Secondary color of text.")
        .defaultValue(new SettingColor(175, 175, 175))
        .build()
    );

    private final Setting<Keybind> toggleKeybind = sgGeneral.add(new KeybindSetting.Builder()
        .name("toggle-keybind")
        .description("Keybind used to toggle HUD.")
        .defaultValue(Keybind.none())
        .action(() -> active = !active)
        .build()
    );

    // Editor

    public final Setting<Integer> snappingRange = sgEditor.add(new IntSetting.Builder()
        .name("snapping-range")
        .description("Snapping range in editor.")
        .defaultValue(6)
        .build()
    );

    private final HudRenderer RENDERER = new HudRenderer();

    public final List<HudElement> elements = new ArrayList<>();
    public final HudElementLayer topLeft, topCenter, topRight, bottomLeft, bottomCenter, bottomRight;

    public final Runnable reset = () -> {
        align();
        elements.forEach(element -> {
            element.active = element.defaultActive;
            element.settings.forEach(group -> group.forEach(Setting::reset));
        });
    };

    public HUD() {
        super("hud");

        settings.registerColorSettings(null);

        // Top Left
        topLeft = new HudElementLayer(RENDERER, elements, AlignmentX.Left, AlignmentY.Top, 2, 2);
        topLeft.add(new LogoHud(this));
        topLeft.add(new WatermarkHud(this));
        topLeft.add(new FpsHud(this));
        topLeft.add(new PingHud(this));
        topLeft.add(new TpsHud(this));
        topLeft.add(new SpeedHud(this));
        topLeft.add(new BiomeHud(this));
        topLeft.add(new TimeHud(this));
        topLeft.add(new ServerHud(this));
        topLeft.add(new DurabilityHud(this));
        topLeft.add(new BreakingBlockHud(this));
        topLeft.add(new LookingAtHud(this));

        // Top Center
        topCenter = new HudElementLayer(RENDERER, elements, AlignmentX.Center, AlignmentY.Top, 0, 2);
        topCenter.add(new WelcomeHud(this));
        topCenter.add(new LagNotifierHud(this));

        // Top Right
        topRight = new HudElementLayer(RENDERER, elements, AlignmentX.Right, AlignmentY.Top, 2, 2);
        topRight.add(new ActiveModulesHud(this));


        // Bottom Left
        bottomLeft = new HudElementLayer(RENDERER, elements, AlignmentX.Left, AlignmentY.Bottom, 2, 2);
        bottomLeft.add(new PlayerModelHud(this));

        // Bottom Center
        bottomCenter = new HudElementLayer(RENDERER, elements, AlignmentX.Center, AlignmentY.Bottom, 48, 64);
        bottomCenter.add(new ArmorHud(this));
        bottomCenter.add(new CompassHud(this));

        // Bottom Right
        bottomRight = new HudElementLayer(RENDERER, elements, AlignmentX.Right, AlignmentY.Bottom, 2, 2);
        bottomRight.add(new PositionHud(this));
        bottomRight.add(new RotationHud(this));
        bottomRight.add(new PotionTimersHud(this));

        align();
    }

    public static HUD get() {
        return Systems.get(HUD.class);
    }

    private void align() {
        RENDERER.begin(scale.get(), 0, true);

        topLeft.align();
        topCenter.align();
        topRight.align();
        bottomLeft.align();
        bottomCenter.align();
        bottomRight.align();

        RENDERER.end();
    }

    @EventHandler
    public void onRender(Render2DEvent event) {
        if (isEditorScreen()) {
            render(event.tickDelta, hudElement -> true);
        }
        else if (active && !AsteroidClient.mc.options.hudHidden && !AsteroidClient.mc.options.debugEnabled) {
            render(event.tickDelta, hudElement -> hudElement.active);
        }
    }

    public void render(float delta, Predicate<HudElement> shouldRender) {
        RENDERER.begin(scale.get(), delta, false);

        for (HudElement element : elements) {
            if (shouldRender.test(element)) {
                element.update(RENDERER);
                element.render(RENDERER);
            }
        }

        RENDERER.end();
    }

    public static boolean isEditorScreen() {
        return AsteroidClient.mc.currentScreen instanceof HudEditorScreen || AsteroidClient.mc.currentScreen instanceof HudElementScreen;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putBoolean("active", active);
        tag.put("settings", settings.toTag());
        tag.put("elements", NbtUtils.listToTag(elements));

        return tag;
    }

    @Override
    public HUD fromTag(NbtCompound tag) {
        settings.reset();

        if (tag.contains("active")) active = tag.getBoolean("active");
        if (tag.contains("settings")) settings.fromTag(tag.getCompound("settings"));
        if (tag.contains("elements")) {
            NbtList elementsTag = tag.getList("elements", 10);

            for (NbtElement t : elementsTag) {
                NbtCompound elementTag = (NbtCompound) t;

                for (HudElement element : elements) {
                    if (element.name.equals(elementTag.getString("name"))) {
                        element.fromTag(elementTag);
                        break;
                    }
                }
            }
        }

        return super.fromTag(tag);
    }
}
