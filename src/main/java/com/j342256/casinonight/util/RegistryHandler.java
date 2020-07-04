package com.j342256.casinonight.util;

import com.j342256.casinonight.CasinoNight;
import com.j342256.casinonight.blocks.BlockItemBase;
import com.j342256.casinonight.blocks.BlackJackBlock;
import com.j342256.casinonight.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, CasinoNight.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, CasinoNight.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainerTypes.CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    public static final RegistryObject<Item> CHIPS = ITEMS.register("chips", ItemBase::new);

    // Blocks
    public static final RegistryObject<Block> BLACK_JACK = BLOCKS.register("black_jack", () -> new BlackJackBlock(Block.Properties.create(Material.CLAY)));

    // Block Items
    public static final RegistryObject<Item> BLACK_JACK_ITEM = ITEMS.register("black_jack", () -> new BlockItemBase(BLACK_JACK.get()));
}
