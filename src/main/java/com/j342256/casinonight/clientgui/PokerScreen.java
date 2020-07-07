package com.j342256.casinonight.clientgui;

import com.j342256.casinonight.CasinoNight;
import com.j342256.casinonight.container.PokerContainer;
import com.j342256.casinonight.helper.PokerLogic;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PokerScreen extends ContainerScreen<PokerContainer> implements Button.IPressable, IRenderable {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(CasinoNight.MOD_ID, "textures/gui/poker.png");
    private static final ResourceLocation CARDS = new ResourceLocation(CasinoNight.MOD_ID, "textures/gui/cards.png");
    private PokerLogic game = new PokerLogic();

    public PokerScreen(PokerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 256;
        this.ySize = 256;
    }
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void init(){
        super.init();
        game.init();
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
    }
    @Override
    public void onPress(Button button) {
    }
}
