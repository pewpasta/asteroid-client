package RewardedIvan.asteroidclient.mixin.sodium;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = LightDataAccess.class, remap = false)
public class LightDataAccessMixin {
    private static final int FULL_LIGHT = 15 << 20 | 15 << 4;

    @Shadow protected BlockRenderView world;
    @Shadow @Final private BlockPos.Mutable pos;
}
