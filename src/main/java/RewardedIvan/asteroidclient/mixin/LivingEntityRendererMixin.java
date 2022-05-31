/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package RewardedIvan.asteroidclient.mixin;

import RewardedIvan.asteroidclient.utils.player.PlayerUtils;
import RewardedIvan.asteroidclient.utils.player.Rotations;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.systems.modules.render.NoRender;
import RewardedIvan.asteroidclient.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static RewardedIvan.asteroidclient.AsteroidClient.mc;
import static org.lwjgl.opengl.GL11.*;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Shadow @Nullable
    protected abstract RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline);

    //3rd Person Rotation

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 2, at = @At(value = "STORE", ordinal = 0))
    public float changeYaw(float oldValue, LivingEntity entity) {
        if (entity.equals(mc.player) && Rotations.rotationTimer < 10) return Rotations.serverYaw;
        return oldValue;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 3, at = @At(value = "STORE", ordinal = 0))
    public float changeHeadYaw(float oldValue, LivingEntity entity) {
        if (entity.equals(mc.player) && Rotations.rotationTimer < 10) return Rotations.serverYaw;
        return oldValue;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 5, at = @At(value = "STORE", ordinal = 3))
    public float changePitch(float oldValue, LivingEntity entity) {
        if (entity.equals(mc.player) && Rotations.rotationTimer < 10) return Rotations.serverPitch;
        return oldValue;
    }

    // Player model rendering in main menu

    @Redirect(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"))
    private AbstractTeam hasLabelClientPlayerEntityGetScoreboardTeamProxy(ClientPlayerEntity player) {
        if (player == null) return null;
        return player.getScoreboardTeam();
    }
}
