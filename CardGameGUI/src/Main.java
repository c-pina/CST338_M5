import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main {

   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
         
   public static void main(String[] args)
   {         
      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Deck deck = new Deck(1);
      deck.shuffle();
      // show everything to the user
      myCardTable.setVisible(true);
      
      // CREATE LABELS ----------------------------------------------------
      Card myCard = new Card();
      for (int i = 0; i < NUM_CARDS_PER_HAND; i ++)
      {
         myCard=deck.dealCard();
         computerLabels[i] = new JLabel(CardGUI.getBackCardIcon(), JLabel.CENTER);
         humanLabels[i] = new JLabel(CardGUI.getIcon(myCard), JLabel.CENTER);
      }
      myCard=deck.dealCard();
      playedCardLabels[0] = new JLabel(CardGUI.getIcon(myCard), JLabel.CENTER);
      myCard=deck.dealCard();
      playedCardLabels[1] = new JLabel(CardGUI.getIcon(myCard), JLabel.CENTER);
      playedCardLabels[0].setVerticalAlignment(JLabel.BOTTOM);
      playedCardLabels[1].setVerticalAlignment(JLabel.BOTTOM);
      
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);
      playLabelText[0].setVerticalAlignment(JLabel.TOP);
      playLabelText[1].setVerticalAlignment(JLabel.TOP);

      
        
     // ADD LABELS TO PANELS -----------------------------------------
     for (int j = 0; j < NUM_CARDS_PER_HAND; j++)
     {
        myCardTable.pnlComputerHand.add(computerLabels[j]);
        myCardTable.pnlHumanHand.add(humanLabels[j]);
     }
            
      // and two random cards in the play region (simulating a computer/hum ply)
      myCardTable.pnlPlayArea.add(playedCardLabels[0]);
      myCardTable.pnlPlayArea.add(playedCardLabels[1]);
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);


      // show everything to the user
      myCardTable.setVisible(true);
   }
}
