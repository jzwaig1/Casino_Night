package com.j342256.casinonight.util;

import com.j342256.casinonight.CasinoNight;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = CasinoNight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {
    public static void init(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
    }
}
