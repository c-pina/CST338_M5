import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main 
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   
   CardGameFramework highCardGame;
   CardTable myCardTable;
   
   public void startGame()
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;    

      // establish main frame in which program will run
      this.myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      this.myCardTable.setSize(800, 600);
      this.myCardTable.setLocationRelativeTo(null);
      this.myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.highCardGame = new CardGameFramework(
            numPacksPerDeck, 
            numJokersPerPack,  
            numUnusedCardsPerPack, 
            unusedCardsPerPack, 
            NUM_PLAYERS, 
            NUM_CARDS_PER_HAND);

      this.highCardGame.deal();      

      // CREATE LABELS ----------------------------------------------------
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         JLabel computerLabel = new JLabel(CardGUI.getBackCardIcon());
         computerLabels[i] = computerLabel;

         // create the player label
         Card playerCard = this.highCardGame.getHand(0).inspectCard(i);
         JLabel playerLabel = this.labelForCard(playerCard, i);
         this.humanLabels[i] = playerLabel;
      }
      
      // ADD LABELS TO PANELS -----------------------------------------
      for (int j = 0; j < NUM_CARDS_PER_HAND; j++)
      {
         this.myCardTable.pnlComputerHand.add(computerLabels[j], JLabel.CENTER);
         JLabel label = this.humanLabels[j];
         this.myCardTable.pnlHumanHand.add(label, JLabel.CENTER);
      }
      
      this.displayTurnLabelForHuman(true);

      // show everything to the user
      this.myCardTable.setVisible(true);  
   }
   
   private void displayTextOnLabel(String text)
   {
      if (playLabelText[0] != null) 
      {
         this.myCardTable.pnlPlayArea.remove(playLabelText[0]);
      }

      JLabel label = new JLabel(text, JLabel.CENTER);
      label.setVerticalAlignment(JLabel.TOP);
      this.myCardTable.pnlPlayArea.add(label);
      playLabelText[0] = label;
      this.myCardTable.setVisible(true);
   }
   
   private void displayTurnLabelForHuman(Boolean isHuman)
   {
      // add it again, designating turn
      if (isHuman)
      {
         this.displayTextOnLabel("Your Turn");
      }
      else
      {
         this.displayTextOnLabel("Computer's Turn");
      }
   }
   
   private void handleAction(ActionEvent e)
   {
      if (!this.HaveAllCardsBeenPlayed())
      {
         // get the tapped card from the human's hand and play it
         int cardIndex = Integer.parseInt(e.getActionCommand());
         Card card = this.highCardGame.playCard(0, cardIndex);

         // add card to the playing area
         JLabel playedCardLabel = new JLabel(CardGUI.getIcon(card));
         this.myCardTable.pnlPlayArea.add(playedCardLabel, JLabel.CENTER);
         this.displayTurnLabelForHuman(false);
         this.reloadHumanHand();
         this.PlayComputerHand();   
      } 
      else
      {
         this.displayTextOnLabel("Game Over!");
      }
   }
   
   private void PlayComputerHand()
   {
      Hand hand = this.highCardGame.getHand(1);
      Card card = this.highCardGame.playCard(1, 0);
      System.out.println(card);
      
      // add card to view
      JLabel playedCardLabel = new JLabel(CardGUI.getIcon(card));
      this.myCardTable.pnlPlayArea.add(playedCardLabel, JLabel.CENTER);
      
      // remove all the cards from the computer's hand and reload
      this.myCardTable.pnlComputerHand.removeAll();
      
      Card[] validCards = hand.validCards();
      computerLabels = new JLabel[validCards.length];

      for (int i = 0; i < validCards.length; i++)
      {
         Card localCard = validCards[i];
         if (localCard != null)
         {
            JLabel computerLabel = new JLabel(CardGUI.getBackCardIcon());
            computerLabels[i] = computerLabel;
            this.myCardTable.pnlComputerHand.add(computerLabel);
         }
      }
      
      this.displayTurnLabelForHuman(true);
   }
    
   public void reloadHumanHand()
   {      
      // remove all the cards from the player's hand
      this.myCardTable.pnlHumanHand.removeAll();
      
      // clear out the labels array
      Hand hand = this.highCardGame.getHand(0);
      this.humanLabels = new JLabel[hand.getNumCards()];
      
      // re-index the cards onto the view
      Card[] validCards = hand.validCards();
 
      for (int i = 0; i < validCards.length; i++)
      {
         // create the label
         Card validCard = validCards[i];
         if (validCard == null)
         {
            break;
         }

         JLabel label = this.labelForCard(validCard, i);
         this.humanLabels[i] = label;

         // now add the card onto the view
         this.myCardTable.pnlHumanHand.add(this.humanLabels[i], JLabel.CENTER);   
      }
      
      this.myCardTable.setVisible(true);
   }
   
   private Boolean HaveAllCardsBeenPlayed()
   {
      return this.highCardGame.getHand(0).getNumCards() == 0 && this.highCardGame.getHand(1).getNumCards() == 0;
   }

   private JLabel labelForCard(Card card, int index)
   {
      JLabel label = new JLabel(CardGUI.getIcon(card));

      // add the button
      JButton btn1 = new JButton(Integer.toString(index));
      btn1.setBounds(0, 65, 30, 30);
      btn1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            handleAction(e);
         }          
      });

      label.add(btn1, JLabel.CENTER);
      return label;
   }
   
   public static void main(String[] args)
   {     
      Main main = new Main();
      main.startGame();
   }
}
