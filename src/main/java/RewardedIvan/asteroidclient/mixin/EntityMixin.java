package RewardedIvan.asteroidclient.mixin;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.events.entity.LivingEntityMoveEvent;
import RewardedIvan.asteroidclient.events.entity.player.JumpVelocityMultiplierEvent;
import RewardedIvan.asteroidclient.events.entity.player.PlayerMoveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static RewardedIvan.asteroidclient.AsteroidClient.mc;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public World world;

    @Shadow public abstract BlockPos getBlockPos();
    @Shadow protected abstract BlockPos getVelocityAffectingPos();

    @Shadow
    public abstract void emitGameEvent(GameEvent event);

    @Inject(method = "getJumpVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    private void onGetJumpVelocityMultiplier(CallbackInfoReturnable<Float> info) {
        if ((Object) this == mc.player) {
            float f = world.getBlockState(getBlockPos()).getBlock().getJumpVelocityMultiplier();
            float g = world.getBlockState(getVelocityAffectingPos()).getBlock().getJumpVelocityMultiplier();
            float a = f == 1.0D ? g : f;

            JumpVelocityMultiplierEvent event = AsteroidClient.EVENT_BUS.post(JumpVelocityMultiplierEvent.get());
            info.setReturnValue(a * event.multiplier);
        }
    }

    @Inject(method = "move", at = @At("HEAD"))
    private void onMove(MovementType type, Vec3d movement, CallbackInfo info) {
        if ((Object) this == mc.player) {
            AsteroidClient.EVENT_BUS.post(PlayerMoveEvent.get(type, movement));
        } else if ((Object) this instanceof LivingEntity) {
            AsteroidClient.EVENT_BUS.post(LivingEntityMoveEvent.get((LivingEntity) (Object) this, movement));
        }
    }

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void onIsInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (player == null) info.setReturnValue(false);
    }
}
