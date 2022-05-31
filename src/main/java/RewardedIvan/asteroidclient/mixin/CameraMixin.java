package RewardedIvan.asteroidclient.mixin;

import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.mixininterface.ICamera;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.modules.render.FreeLook;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public abstract class CameraMixin implements ICamera {
    @Shadow private boolean thirdPerson;

    @Shadow protected abstract double clipToSpace(double desiredCameraDistance);

    @Shadow private float yaw;
    @Shadow private float pitch;

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Unique private float tickDelta;

    @Inject(method = "update", at = @At("HEAD"))
    private void onUpdateHead(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        this.tickDelta = tickDelta;
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
    private void onUpdateSetRotationArgs(Args args) {
        FreeLook freeLook = Modules.get().get(FreeLook.class);

        if (freeLook.isActive()) {
            args.set(0, freeLook.cameraYaw);
            args.set(1, freeLook.cameraPitch);
        }
    }

    @Override
    public void setRot(double yaw, double pitch) {
        setRotation((float) yaw, (float) Utils.clamp(pitch, -90, 90));
    }
}
