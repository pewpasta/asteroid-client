package RewardedIvan.asteroidclient.utils.render;

import RewardedIvan.asteroidclient.AsteroidClient;
import RewardedIvan.asteroidclient.mixin.WorldRendererAccessor;
import RewardedIvan.asteroidclient.renderer.GL;
import RewardedIvan.asteroidclient.renderer.PostProcessRenderer;
import RewardedIvan.asteroidclient.renderer.Shader;
import RewardedIvan.asteroidclient.renderer.ShapeMode;
import RewardedIvan.asteroidclient.systems.modules.Modules;
import RewardedIvan.asteroidclient.utils.Init;
import RewardedIvan.asteroidclient.utils.InitStage;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;

public class EntityShaders {
    // Overlay
    public static Framebuffer overlayFramebuffer;
    public static OutlineVertexConsumerProvider overlayVertexConsumerProvider;
    private static Shader overlayShader;
    public static float timer;

    // Outline
    public static Framebuffer outlinesFramebuffer;
    public static OutlineVertexConsumerProvider outlinesVertexConsumerProvider;
    private static Shader outlinesShader;

    public static boolean renderingOutlines;

    // Overlay

    public static void initOverlay(String shaderName) {
        overlayShader = new Shader("outline.vert", shaderName + ".frag");
        overlayFramebuffer = new SimpleFramebuffer(AsteroidClient.mc.getWindow().getFramebufferWidth(), AsteroidClient.mc.getWindow().getFramebufferHeight(), false, false);
        overlayVertexConsumerProvider = new OutlineVertexConsumerProvider(AsteroidClient.mc.getBufferBuilders().getEntityVertexConsumers());
        timer = 0;
    }

    // Outlines

    @Init(stage = InitStage.Pre)
    public static void initOutlines() {
        outlinesShader = new Shader("outline.vert", "outline.frag");
        outlinesFramebuffer = new SimpleFramebuffer(AsteroidClient.mc.getWindow().getFramebufferWidth(), AsteroidClient.mc.getWindow().getFramebufferHeight(), false, false);
        outlinesVertexConsumerProvider = new OutlineVertexConsumerProvider(AsteroidClient.mc.getBufferBuilders().getEntityVertexConsumers());
    }

    // Main

    public static void endRender() {
        WorldRenderer worldRenderer = AsteroidClient.mc.worldRenderer;
        WorldRendererAccessor wra = (WorldRendererAccessor) worldRenderer;
        Framebuffer fbo = worldRenderer.getEntityOutlinesFramebuffer();
    }

    public static void onResized(int width, int height) {
        if (overlayFramebuffer != null) overlayFramebuffer.resize(width, height, false);
        if (outlinesFramebuffer != null) outlinesFramebuffer.resize(width, height, false);
    }

    public static void renderOutlines(Runnable draw, boolean entities, int width, float fillOpacity, ShapeMode shapeMode) {
        WorldRenderer worldRenderer = AsteroidClient.mc.worldRenderer;
        WorldRendererAccessor wra = (WorldRendererAccessor) worldRenderer;
        Framebuffer fbo = worldRenderer.getEntityOutlinesFramebuffer();

        if (entities) wra.setEntityOutlinesFramebuffer(outlinesFramebuffer);
        else {
            outlinesFramebuffer.clear(false);
            outlinesFramebuffer.beginWrite(false);
        }
        draw.run();
        if (entities) wra.setEntityOutlinesFramebuffer(fbo);

        AsteroidClient.mc.getFramebuffer().beginWrite(false);

        GL.bindTexture(outlinesFramebuffer.getColorAttachment());

        outlinesShader.bind();
        outlinesShader.set("u_Size", AsteroidClient.mc.getWindow().getFramebufferWidth(), AsteroidClient.mc.getWindow().getFramebufferHeight());
        outlinesShader.set("u_Texture", 0);
        outlinesShader.set("u_Width", width);
        outlinesShader.set("u_FillOpacity", fillOpacity / 255.0);
        outlinesShader.set("u_ShapeMode", shapeMode.ordinal());
        PostProcessRenderer.render();
    }
}
