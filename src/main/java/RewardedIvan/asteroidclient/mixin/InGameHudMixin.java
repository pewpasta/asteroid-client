/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.mixin;

import RewardedIvan.asteroidclient.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.events.render.Render2DEvent;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.modules.render.NoRender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void clear();

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        client.getProfiler().push("asteroid-client_render_2d");

        Utils.unscaledProjection();

        AsteroidClient.EVENT_BUS.post(Render2DEvent.get(scaledWidth, scaledHeight, tickDelta));

        Utils.scaledProjection();
        RenderSystem.applyModelViewMatrix();

        client.getProfiler().pop();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderStatusEffectOverlay(CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noPotionIcons()) info.cancel();
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/util/Identifier;F)V", ordinal = 0))
    private void onRenderPumpkinOverlay(Args args) {
        if (Modules.get().get(NoRender.class).noPumpkinOverlay()) args.set(1, 0f);
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/util/Identifier;F)V", ordinal = 1))
    private void onRenderPowderedSnowOverlay(Args args) {
        if (Modules.get().get(NoRender.class).noPowderedSnowOverlay()) args.set(1, 0f);
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderVignetteOverlay(Entity entity, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noVignette()) info.cancel();
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void onRenderScoreboardSidebar(MatrixStack matrixStack, ScoreboardObjective scoreboardObjective, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noScoreboard()) info.cancel();
    }

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void onRenderSpyglassOverlay(float scale, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noSpyglassOverlay()) info.cancel();
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void onRenderCrosshair(MatrixStack matrices, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noCrosshair()) info.cancel();
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    private void onRenderHeldItemTooltip(MatrixStack matrices, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noHeldItemName()) info.cancel();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasJumpingMount()Z"))
    private boolean onSwitchBar(ClientPlayerEntity player) {
        if (!Modules.get().get(NoRender.class).noMountHiding() || !client.interactionManager.hasExperienceBar()) return player.hasJumpingMount();
        return player.hasJumpingMount() && client.options.jumpKey.isPressed() || player.getMountJumpStrength() > 0;
    }

    @ModifyConstant(method = "renderMountHealth", constant = @Constant(intValue = 39))
    private int onRenderMountHelath(int yOffset) {
        return Modules.get().get(NoRender.class).noMountHiding() && client.interactionManager.hasStatusBars() ? yOffset + 10 : yOffset;
    }

    @ModifyVariable(method = "renderStatusBars", at = @At(value = "STORE", ordinal = 1), ordinal = 10)
    private int onRenderStatusBars(int y) {
        return Modules.get().get(NoRender.class).noMountHiding() && client.player.hasJumpingMount() ? y - 10 : y;
    }

    @ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I", ordinal = 0))
    private LivingEntity modifyGetHeartCount(LivingEntity entity) {
        return Modules.get().get(NoRender.class).noMountHiding() ? null : entity;
    }
}
