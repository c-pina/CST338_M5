import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import Card.Suit;

public class Main 
{
   public enum HandResult {
      Lose,
      Win,
      Tie
   }
   
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   
   CardGameFramework highCardGame;
   CardTable myCardTable;
   
   ArrayList<Card> cardsComputerHasCaptured = new ArrayList<Card>();
   ArrayList<Card> cardsHumanHasCaptured = new ArrayList<Card>();
   
   Card lastPlayedHumanCard = null;
   Card lastPlayedComputerCard = null;
   
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
         this.myCardTable.pnlGameStatus.remove(playLabelText[0]);
      }

      JLabel label = new JLabel(text, JLabel.CENTER);
      label.setVerticalAlignment(JLabel.TOP);
      this.myCardTable.pnlGameStatus.add(label);
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
      // get the tapped card from the human's hand and play it
      int cardIndex = Integer.parseInt(e.getActionCommand());
      this.lastPlayedHumanCard = this.highCardGame.playCard(0, cardIndex);
      
      // remove all cards from the playing area
      this.myCardTable.pnlPlayArea.removeAll();

      // add card to the playing area
      JLabel playedCardLabel = new JLabel(CardGUI.getIcon(this.lastPlayedHumanCard));
      this.myCardTable.pnlPlayArea.add(playedCardLabel, JLabel.CENTER);
      this.reloadHumanHand();
      this.playComputerHand();
      this.checkGameResults();
   }
   
   private void checkGameResults()
   {
      if (this.highCardGame.getHand(0).getNumCards() == 0 && this.highCardGame.getHand(1).getNumCards() == 0)
      {
         String resultsString = null;
         
         if (this.cardsHumanHasCaptured.size() > this.cardsComputerHasCaptured.size())
         {
            resultsString = "Game Over -- You won by capturing " + this.cardsHumanHasCaptured.size() + " of the computer's cards!";  
         }
         else if (this.cardsHumanHasCaptured.size() < this.cardsComputerHasCaptured.size())
         {
            resultsString = "Game Over -- You lost! Computer captured " + this.cardsComputerHasCaptured.size() + " of your cards!";
         }
         else
         {
            resultsString = "Game over -- Tie Game!";
         }
         
         this.displayTextOnLabel(resultsString);
      }
   }
   
   private HandResult handResultForHumanCard(Card humanCard, Card computerCard)
   {
      char humanValue = humanCard.getValue();
      char computerValue = computerCard.getValue();
      
      // cards are equal, joker ties (only case), otherwise rank wins
      if (humanValue == computerValue)
      {
         if (humanValue == 'X')
         {
            return HandResult.Tie;
         }
         
         if (humanCard.getSuit().getValue() > computerCard.getSuit().getValue())
         {
            return HandResult.Win;
         }
         else
         {
            return HandResult.Lose;
         }
      }
      else
      {
         // cards aren't equal, joker wins, otherwise card value wins
         if (humanValue == 'X')
         {
            return HandResult.Win;
         }
         
         if (computerValue == 'X')
         {
            return HandResult.Lose;
         }
         
         // check for Ace, special case
         if (humanValue == 'A')
         {
            return HandResult.Win; 
         }
         
         if (computerValue == 'A')
         {
            return HandResult.Lose; 
         }
         
         // now all others
         if (humanValue > computerValue)
         {
            return HandResult.Win;
         }
         else
         {
            return HandResult.Lose;
         }
      }
   }
   
   private void determineWinningHand()
   {
      HandResult result = this.handResultForHumanCard(this.lastPlayedHumanCard, this.lastPlayedComputerCard);
      
      // human won, add to the pot
      if (result == HandResult.Win)
      {
         this.displayTextOnLabel("Win");
         this.cardsHumanHasCaptured.add(this.lastPlayedComputerCard);
         JLabel wonCard = new JLabel(CardGUI.getIcon(this.lastPlayedComputerCard));
         this.myCardTable.pnlHumanPot.add(wonCard, JLabel.CENTER);
      } 
      else if (result == HandResult.Lose)
      {
         this.cardsComputerHasCaptured.add(this.lastPlayedHumanCard);
         this.displayTextOnLabel("Lose");
      }
      else
      {
         this.displayTextOnLabel("Tie");
      }
      
      this.myCardTable.setVisible(true);
   }
   
   private void playComputerHand()
   {
      Hand hand = this.highCardGame.getHand(1);
      this.lastPlayedComputerCard = this.highCardGame.playCard(1, 0);
      
      // add card to view
      JLabel playedCardLabel = new JLabel(CardGUI.getIcon(this.lastPlayedComputerCard));
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
      
      this.determineWinningHand();
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
