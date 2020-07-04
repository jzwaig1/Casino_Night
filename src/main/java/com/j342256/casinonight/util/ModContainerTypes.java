package com.j342256.casinonight.util;

import com.j342256.casinonight.CasinoNight;
import com.j342256.casinonight.container.BlackJackContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES =
            new DeferredRegister<>(ForgeRegistries.CONTAINERS, CasinoNight.MOD_ID);
    public static final RegistryObject<ContainerType<BlackJackContainer>> BLACK_JACK =
            CONTAINER_TYPES.register("black_jack", () -> IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new BlackJackContainer(windowId, world, pos, inv, inv.player);
            }));
}
