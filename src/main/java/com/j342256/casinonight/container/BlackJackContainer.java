package com.j342256.casinonight.container;

import com.j342256.casinonight.tileentity.BlackJackTileEntity;
import com.j342256.casinonight.util.BlackJackPacket;
import com.j342256.casinonight.util.ModContainerTypes;
import com.j342256.casinonight.util.Networking;
import com.j342256.casinonight.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BlackJackContainer extends Container implements IItemProvider {
    public BlackJackTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;
    private IItemHandler playerInv;
    private PlayerEntity playerEntity;
    private BlockPos pos;
    public Item item;
    public boolean open = true;
    public ItemStack bet = ItemStack.EMPTY;

    public BlackJackContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory,
                              PlayerEntity player) {
        super(ModContainerTypes.BLACK_JACK.get(), windowId);
        this.pos = pos;
        tileEntity = (BlackJackTileEntity) world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInv = new InvWrapper(playerInventory);
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
        this.item = Items.DIAMOND;
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 70, 195) {
                    @Override
                    @OnlyIn(Dist.CLIENT)
                    public boolean isEnabled() {
                        if(open) {
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                });
                addSlot(new SlotItemHandler(h, 1, 70, 175));
            });
        }
        int startX = 70;
        int startY = 225;
        int deltaX = 18;
        for (int i=0;i<9;++i) {
            addSlot(new SlotItemHandler(playerInv, i, startX + (i * deltaX), startY));
        }
/*
        this.addSlot(new Slot( tileEntity, 0, 70, 195));
        this.addSlot(new Slot( tileEntity, 1, 70, 175));
        ItemStack prize = new ItemStack(this.asItem(),2);
        this.tileEntity.setInventorySlotContents(0,prize);

*/
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, RegistryHandler.BLACK_JACK.get());
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

    public void holdBet() {
        this.bet = this.inventorySlots.get(0).getStack();
        takeBet();
    }
    public void takeBet() {
        Networking.sendToServer(new BlackJackPacket(this.pos, ItemStack.EMPTY, 0));
    }
    public void returnBet() {
        Networking.sendToServer(new BlackJackPacket(this.pos, this.bet, 0));
    }
    public void giveWinnings(){
        Networking.sendToServer(new BlackJackPacket(this.pos, this.bet, 1));
        Networking.sendToServer(new BlackJackPacket(this.pos, this.bet, 0));
    }

    @Override
    public Item asItem() {
        return this.item;
    }
}
