package com.j342256.casinonight.clientgui;

import com.j342256.casinonight.CasinoNight;
import com.j342256.casinonight.container.BlackJackContainer;
import com.j342256.casinonight.helper.BlackJackLogic;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackJackScreen extends ContainerScreen<BlackJackContainer> implements Button.IPressable, IRenderable {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(CasinoNight.MOD_ID, "textures/gui/black_jack.png");
    private static final ResourceLocation CARDS = new ResourceLocation(CasinoNight.MOD_ID, "textures/gui/cards.png");

    public boolean busted = false;
    private int result = 100;
    public BlackJackLogic game = new BlackJackLogic();
    private int rngDealer = 0;
    private int rngPlayer1 = 0;
    private int rngPlayer2 = 0;

    public BlackJackScreen(BlackJackContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
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
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.addButton(new Button(x+10, y+185, 50,16,"Play", this));
        this.addButton(new Button(x+10, y+205, 50,16,"Hit", this));
        this.buttons.get(1).active = false;
        this.addButton(new Button(x+10, y+225, 50,16,"Stay", this));
        this.buttons.get(2).active = false;
        this.game.init();
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
        int deltaX = 0;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
        this.minecraft.getTextureManager().bindTexture(CARDS);
        for(int i=0;i<game.dealerHand.size();++i) {
            this.blit(x + 10 + deltaX, y + 10, game.dealerHand.get(i)[0], game.dealerHand.get(i)[1], 28, 39);
            deltaX += 30;
        }
        deltaX = 0;
        for(int i=0;i<game.playerHand.size();++i) {
            this.blit(x + 100 + deltaX, y + 180, game.playerHand.get(i)[0], game.playerHand.get(i)[1], 28, 39);
            deltaX += 30;
        }
        this.font.drawString(String.valueOf(game.getPlayerScore()),x+115.0f,y+165.0f,4210752);
        this.font.drawString(String.valueOf(game.getDealerScore()),x+115.0f,y+60.0f,4210752);
        if (busted){
            this.font.drawString("YOU BUSTED!",x+100.0f,y+80.0f,4210752);
            this.buttons.get(0).active = true;
            this.buttons.get(1).active = false;
            this.buttons.get(2).active = false;
        }
        if (result < 0){
            this.font.drawString("DEALER WINS!",x+100.0f,y+80.0f,4210752);
            this.buttons.get(0).active = true;
            this.buttons.get(1).active = false;
            this.buttons.get(2).active = false;
        }
        if (result == 0){
            this.font.drawString("ITS A PUSH!",x+100.0f,y+80.0f,4210752);
            this.buttons.get(0).active = true;
            this.buttons.get(1).active = false;
            this.buttons.get(2).active = false;
        }
        if (result > 0 && result != 100){
            this.font.drawString("YOU WIN!",x+100.0f,y+80.0f,4210752);
            this.buttons.get(0).active = true;
            this.buttons.get(1).active = false;
            this.buttons.get(2).active = false;
        }
    }
    private void resetGame(){
        result = 100;
        busted = false;
        game.resetGame();
    }
    @Override
    public void onPress(Button button) {
        if (button == this.buttons.get(0)) {
            resetGame();
            // Checks for Jokers and re-draws if any Joker is drawn.
            do {
                rngDealer = ((int) (Math.random() * 1000) % 54);
                rngPlayer1 = ((int) (Math.random() * 1000) % 54);
                rngPlayer2 = ((int) (Math.random() * 1000) % 54);
            } while(game.cardPos[rngDealer][2] == 0 || game.cardPos[rngPlayer1][2] == 0 || game.cardPos[rngPlayer2][2] == 0);
            game.createDealerHand(game.cardPos[rngDealer]);
            game.createPlayerHand(game.cardPos[rngPlayer1], game.cardPos[rngPlayer2]);
            button.active = false;
            this.buttons.get(1).active = true;
            this.buttons.get(2).active = true;
            this.container.generateItem(Items.DIAMOND,1);
        }
        if (button == this.buttons.get(1)) {
            do {
                rngPlayer1 = ((int) (Math.random() * 1000) % 54);
            } while(game.cardPos[rngPlayer1][2] == 0);
            game.playerHit(game.cardPos[rngPlayer1]);
            if (game.getPlayerScore() > 21){
                busted = true;
            }
        }
        if (button == this.buttons.get(2)) {
            result = game.dealerPlay();
        }
    }
}