package net.nullean.reach.event;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.nullean.reach.Reach;

import net.nullean.reach.client.ReachModelLayers;
import net.nullean.reach.client.ReachRenderInfo;
import net.nullean.reach.client.model.BlemishModel;
import net.nullean.reach.client.model.ModelSpreader;
import net.nullean.reach.client.model.MooBooModel;
import net.nullean.reach.client.renderer.BlemishRenderer;
import net.nullean.reach.client.renderer.MooBooRenderer;
import net.nullean.reach.client.renderer.SpreaderBlastRenderer;
import net.nullean.reach.client.renderer.SpreaderRenderer;
import net.nullean.reach.registry.ReachEntities;
import net.nullean.reach.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Reach.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar
{
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ReachEntities.MOOBOO.get(), MooBooRenderer::new);
        event.registerEntityRenderer(ReachEntities.BLEMISH.get(), BlemishRenderer::new);
        event.registerEntityRenderer(ReachEntities.SPREADER.get(), SpreaderRenderer::new);
        event.registerEntityRenderer(ReachEntities.SPREADER_BLAST.get(), SpreaderBlastRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition layerdefinition = LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64);
        event.registerLayerDefinition(ReachModelLayers.MOOBOO, MooBooModel::createBodyLayer);
        event.registerLayerDefinition(ReachModelLayers.BLEMISH, BlemishModel::createBodyLayer);
        event.registerLayerDefinition(ReachModelLayers.SPREADER, ModelSpreader::createBodyLayer);
    }
    @Mod.EventBusSubscriber(modid = Reach.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {

        }

    }

    @Mod.EventBusSubscriber(modid = Reach.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {

        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {

        }
    }

    @SubscribeEvent
    public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
        ReachRenderInfo renderInfo = new ReachRenderInfo(192.0F, true, DimensionSpecialEffects.SkyType.END, false, false);
        event.register(Reach.prefix("renderer"), renderInfo);
    }
}