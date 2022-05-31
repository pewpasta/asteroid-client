package RewardedIvan.asteroidclient.systems.modules.movement;

import RewardedIvan.asteroidclient.settings.SettingGroup;
import RewardedIvan.asteroidclient.events.world.TickEvent;
import RewardedIvan.asteroidclient.systems.modules.Categories;
import RewardedIvan.asteroidclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class Sprint extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public Sprint() {
        super(Categories.Movement, "sprint", "Automatically sprints.");
    }

    @Override
    public void onDeactivate() {
        mc.player.setSprinting(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player.forwardSpeed > 0) {
            mc.player.setSprinting(true);
        }
    }
}
