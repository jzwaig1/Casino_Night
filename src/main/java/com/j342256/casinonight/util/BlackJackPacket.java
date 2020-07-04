package com.j342256.casinonight.util;

import com.j342256.casinonight.tileentity.BlackJackTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.Supplier;

public class BlackJackPacket {
    private BlockPos pos;
    private ItemStack stack;
    private int slot;

    public BlackJackPacket(PacketBuffer buf) {
        pos = buf.readBlockPos();
        stack = buf.readItemStack();
        slot = buf.readInt();
    }

    public BlackJackPacket(BlockPos pos, ItemStack stack, int slot) {
        this.pos = pos;
        this.stack = stack;
        this.slot = slot;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeItemStack(stack);
        buf.writeInt(slot);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            BlackJackTileEntity tileEntity = (BlackJackTileEntity) ctx.get().getSender().world.getTileEntity(pos);
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                h.insertItem(slot,stack,false);
            });
        });
        return true;
    }
}
