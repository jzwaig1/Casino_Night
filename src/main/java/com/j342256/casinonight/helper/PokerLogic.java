package com.j342256.casinonight.helper;

import java.util.ArrayList;

public class PokerLogic {
    public ArrayList<int[]> cardPos = new ArrayList(54);

    public PokerLogic() { }

    public void init() {
        for (int i=0;i<9;++i){
            for (int j=0;j<6;++j){
                cardPos.add(new int[] {(i * 28),(j * 39)});
            }
        }
    }
}
