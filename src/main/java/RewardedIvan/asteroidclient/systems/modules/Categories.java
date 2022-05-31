package RewardedIvan.asteroidclient.systems.modules;

import RewardedIvan.asteroidclient.addons.AddonManager;
import RewardedIvan.asteroidclient.addons.AsteroidAddon;
import net.minecraft.item.Items;

public class Categories {
    public static final Category Player = new Category("Player", Items.ARMOR_STAND.getDefaultStack());
    public static final Category Movement = new Category("Movement", Items.DIAMOND_BOOTS.getDefaultStack());
    public static final Category Render = new Category("Render", Items.GLASS.getDefaultStack());
    public static final Category World = new Category("World", Items.GRASS_BLOCK.getDefaultStack());
    public static final Category Misc = new Category("Misc", Items.LAVA_BUCKET.getDefaultStack());

    public static boolean REGISTERING;

    public static void init() {
        REGISTERING = true;

        // Asteroid
        Modules.registerCategory(Player);
        Modules.registerCategory(Movement);
        Modules.registerCategory(Render);
        Modules.registerCategory(World);
        Modules.registerCategory(Misc);

        // Addons
        AddonManager.ADDONS.forEach(AsteroidAddon::onRegisterCategories);

        REGISTERING = false;
    }
}
