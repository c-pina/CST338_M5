import javax.swing.*;

public class CardGUI {
   
    private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
    private static Icon iconBack;
    static boolean iconsLoaded = false;
    
    //loads cards icon in 2D array iconCars if iconCards is null
    static void loadCardIcons()
    {
       if(iconCards[0][0] == null)
       {
          CardGameGUI.loadCardIcons();
          for(int j = 0; j < 4; j++)
          {
             for (int k = 0; k < 14; k++)
             {
                iconCards[k][j] = CardGameGUI.icon[(j * (iconCards.length)) + k];
             }
          }
          iconBack = CardGameGUI.icon[CardGameGUI.NUM_CARD_IMAGES - 1];
      }
    }
    
    //returns icon for given card. call loadCardIcons to ensure array is not empty
    static public Icon getIcon(Card card)
    {
       String stringArray = new String(Card.valueRanks);
       
       loadCardIcons();
       
       return iconCards[stringArray.indexOf(card.getValue())][card.getSuit().ordinal()];
    }
    
    //returns iconBack
    static public Icon getBackCardIcon()
    {
       loadCardIcons();
       return iconBack;
    }
}
