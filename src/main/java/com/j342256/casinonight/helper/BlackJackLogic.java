package com.j342256.casinonight.helper;

import java.util.ArrayList;


public class BlackJackLogic {
    private int playerScore = 0;
    private int dealerScore = 0;
    public int[][] cardPos = new int[54][3];
    public ArrayList<int[]> playerHand = new ArrayList();
    public ArrayList<int[]> dealerHand = new ArrayList();
    private final int[] cardVals = {2,2,2,2,10,10,3,3,3,3,10,10,4,4,4,4,10,10,
                                    5,5,5,5,11,11,6,6,6,6,0,0,7,7,7,7,10,10,
                                    8,8,8,8,10,10,9,9,9,9,10,10,10,10,10,10,11,11};

    public BlackJackLogic() { }

    public void init() {
        for (int i=0;i<9;++i){
            for (int j=0;j<6;++j){
                cardPos[(i*6+j)][0] = (i * 28);
                cardPos[(i*6+j)][1] = (j * 39);
                cardPos[(i*6+j)][2] = cardVals[(i*6+j)];
            }
        }
    }
    public void resetGame(){
        playerScore = 0;
        dealerScore = 0;
        playerHand = new ArrayList();
        dealerHand = new ArrayList();
        init();
    }
    public void createPlayerHand(int[] c1, int[] c2){
        playerScore = c1[2] + c2[2];
        playerHand.add(c1);
        playerHand.add(c2);
        for(int i=0;i<playerHand.size();++i){
            if (playerScore > 21) {
                if (playerHand.get(i)[2] == 11) {
                    playerHand.get(i)[2] = 1;
                    playerScore -= 10;
                }
            }
        }
    }
    public void playerHit(int[] c){
        playerHand.add(c);
        playerScore += c[2];
        for(int i=0;i<playerHand.size();++i){
            if (playerScore > 21) {
                if (playerHand.get(i)[2] == 11) {
                    playerHand.get(i)[2] = 1;
                    playerScore -= 10;
                }
            }
        }
    }
    private void dealerHit(){
        int rng = 0;
        do {
            rng = ((int) (Math.random() * 1000) % 54);
        } while(cardPos[rng][2] == 0);
        dealerHand.add(cardPos[rng]);
        dealerScore += cardPos[rng][2];
        for(int i=0;i<dealerHand.size();++i){
            if (dealerScore > 21) {
                if (dealerHand.get(i)[2] == 11) {
                    dealerHand.get(i)[2] = 1;
                    dealerScore -= 10;
                }
            }
        }
    }
    public void createDealerHand(int[] c1){
        dealerScore = c1[2];
        dealerHand.add(c1);
    }
    public int getPlayerScore(){
        return playerScore;
    }
    public int getDealerScore(){
        return dealerScore;
    }
    public int dealerPlay(){
        while (dealerScore < 17){
            dealerHit();
        }
        if (dealerScore > 21){
            return 1;
        }
        return (playerScore - dealerScore);
    }
}
