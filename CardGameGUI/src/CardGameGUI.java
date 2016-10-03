import javax.swing.*;
import java.awt.*;

public class CardGameGUI
{
   static final int NUM_CARD_IMAGES = 57;
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   static void loadCardIcons()
   {
      //Create and add cardBack to icon[]
      icon[NUM_CARD_IMAGES - 1] = new ImageIcon("images/BK.gif");
      
      //Create and add all other cards
      int iconIterator = 0;
      String cardName = "";
      
      for(int j = 0; j < 4; j++)
      {
         for (int k = 1; k < 15; k++)
         {
            cardName = turnIntIntoCardValue(k) + turnIntIntoCardSuit(j) + ".gif";
            icon[iconIterator] = new ImageIcon("images/" + cardName);
            iconIterator++;
         }
      }
   }
   
   static String turnIntIntoCardValue(int k)
   {
      switch(k)
      {
         case 1:
            return "A";
         case 10:
            return "T";
         case 11:
            return "J";
         case 12:
            return "Q";
         case 13:
            return "K";
         case 14: 
            return "X";
         default:
            return Integer.toString(k);
      }
   }
   
   
   static String turnIntIntoCardSuit(int j)
   {
      switch(j)
      {
         case 0:
            return "C";
         case 1:
            return "D";
         case 2:
            return "H";
         case 3:
            return "S";
         default:
            return null;
      }
      
   }
   
   public static void main(String[] args)
   {
      //Load card images onto array
      loadCardIcons();
      
      //Establish main frame
      JFrame mainWindow = new JFrame("Card Room");
      mainWindow.setSize(1150, 650);
      mainWindow.setLocationRelativeTo(null);
      mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //Define window layout to determine component placement
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      mainWindow.setLayout(layout);
      
      //Prepare label array to display cards 
      JLabel[] cardLabels = new JLabel[NUM_CARD_IMAGES];
      for(int i = 0; i < NUM_CARD_IMAGES; i++)
      {
         cardLabels[i] = new JLabel(icon[i]);
      }
      
      //Place labels into frame
      for(int i = 0; i < NUM_CARD_IMAGES; i++)
      {
         mainWindow.add(cardLabels[i]);
      }
      
      //Display window
      mainWindow.setVisible(true);  
   }
}