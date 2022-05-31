package RewardedIvan.asteroidclient.systems.modules.movement;

import RewardedIvan.asteroidclient.settings.BlockListSetting;
import RewardedIvan.asteroidclient.settings.DoubleSetting;
import RewardedIvan.asteroidclient.settings.Setting;
import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.systems.modules.Categories;
import RewardedIvan.asteroidclient.systems.modules.Module;
import net.minecraft.block.Block;

import java.util.List;

public class Slippy extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Double> slippness = sgGeneral.add(new DoubleSetting.Builder()
            .name("slippness")
            .description("Decide how slippery blocks should be")
            .min(0.0)
            .max(1.10)
            .sliderMax(1.10)
            .defaultValue(1.02)
            .build()
    );

    public final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder()
            .name("ignored blocks")
            .description("Decide which blocks not to slip on")
            .defaultValue()
            .build()
    );

    public Slippy() {
        super(Categories.Movement, "slippy", "Makes blocks slippery like ice. (doesn't work on servers)");
    }
}
