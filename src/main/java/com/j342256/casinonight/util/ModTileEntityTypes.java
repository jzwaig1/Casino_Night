package com.j342256.casinonight.util;

import com.j342256.casinonight.CasinoNight;
import com.j342256.casinonight.tileentity.BlackJackTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, CasinoNight.MOD_ID);

    public static final RegistryObject<TileEntityType<BlackJackTileEntity>> BLACK_JACK =
            TILE_ENTITY_TYPES.register("black_jack", () -> TileEntityType.Builder
                    .create(BlackJackTileEntity::new, RegistryHandler.BLACK_JACK.get())
                    .build(null));
}
