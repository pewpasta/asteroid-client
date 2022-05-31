package RewardedIvan.asteroidclient.mixin;

import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.modules.movement.Slippy;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible {
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getSlipperiness", at = @At("RETURN"), cancellable = true)
    public void getSlipperiness(CallbackInfoReturnable<Float> info) {
        // For some retarded reason Tweakeroo calls this method before asteroid is initialized
        if (Modules.get() == null) return;

        Slippy slippy = Modules.get().get(Slippy.class);
        Block block = (Block) (Object) this;

        if (slippy.isActive() && !slippy.blocks.get().contains(block) && MinecraftClient.getInstance().isInSingleplayer()) {
            info.setReturnValue(slippy.slippness.get().floatValue());
        }
    }
}
