package net.nullean.reach.item;

import net.nullean.reach.Reach;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reach.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static CreativeModeTab REACH_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        REACH_TAB = event.registerCreativeModeTab(new ResourceLocation(Reach.MOD_ID, "reach_tab"),
                builder -> builder.icon(() -> new ItemStack(Items.PUMPKIN.asItem())).title(Component.literal("Reach Tab")).build());
    }
}
