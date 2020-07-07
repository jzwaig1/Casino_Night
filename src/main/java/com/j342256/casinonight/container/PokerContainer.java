package com.j342256.casinonight.container;

import com.j342256.casinonight.tileentity.PokerTileEntity;
import com.j342256.casinonight.util.ModContainerTypes;
import com.j342256.casinonight.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.InvWrapper;

public class PokerContainer extends Container implements IItemProvider {
    public PokerTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;
    private IItemHandler playerInv;
    private PlayerEntity playerEntity;
    private BlockPos pos;
    public Item item;
    public boolean open = true;
    public ItemStack bet = ItemStack.EMPTY;

    public PokerContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory,
                              PlayerEntity player) {
        super(ModContainerTypes.POKER.get(), windowId);
        this.pos = pos;
        tileEntity = (PokerTileEntity) world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInv = new InvWrapper(playerInventory);
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, RegistryHandler.POKER.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 36) {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public Item asItem() {
        return this.item;
    }
}

