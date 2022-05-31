package RewardedIvan.asteroidclient.mixin;

import RewardedIvan.asteroidclient.utils.Utils;
import RewardedIvan.asteroidclient.utils.render.NametagUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.events.render.Render3DEvent;
import RewardedIvan.asteroidclient.events.render.RenderAfterWorldEvent;
import RewardedIvan.asteroidclient.mixininterface.IVec3d;
import RewardedIvan.asteroidclient.renderer.Renderer3D;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.modules.render.NoRender;
import RewardedIvan.asteroidclient.systems.modules.render.Zoom;
import RewardedIvan.asteroidclient.utils.render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void updateTargetedEntity(float tickDelta);

    @Shadow public abstract void reset();

    @Shadow @Final private Camera camera;
    @Unique private Renderer3D renderer;

    @Inject(method = "renderWorld", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = { "ldc=hand" }), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci, boolean bl, Camera camera, MatrixStack matrixStack, double d, float f, Matrix4f i, Matrix3f g) {
        if (!Utils.canUpdate()) return;

        client.getProfiler().push("asteroid-client_render");

        if (renderer == null) renderer = new Renderer3D();
        Render3DEvent event = Render3DEvent.get(matrices, renderer, tickDelta, camera.getPos().x, camera.getPos().y, camera.getPos().z);

        RenderUtils.updateScreenCenter();
        NametagUtils.onRender(matrices, i);

        renderer.begin();
        AsteroidClient.EVENT_BUS.post(event);
        renderer.render(matrices);

        RenderSystem.applyModelViewMatrix();
        client.getProfiler().pop();
    }

    @Inject(method = "renderWorld", at = @At("TAIL"))
    private void onRenderWorldTail(CallbackInfo info) {
        AsteroidClient.EVENT_BUS.post(RenderAfterWorldEvent.get());
    }
}
