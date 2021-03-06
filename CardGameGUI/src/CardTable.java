import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class CardTable extends JFrame {

   static int MAX_CARD_PER_HAND = 56;
   static int MAX_PLAYERS = 2;

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, pnlGameStatus, pnlHumanPot;


   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super(title);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());

      pnlComputerHand = new JPanel();
      pnlComputerHand.setLayout(new GridLayout(1,numCardsPerHand));
      TitledBorder cpuBorder = new TitledBorder("Computer");
      cpuBorder.setTitleJustification(TitledBorder.LEFT);
      cpuBorder.setTitlePosition(TitledBorder.TOP);
      pnlComputerHand.setBorder(cpuBorder);
      this.add(pnlComputerHand, BorderLayout.NORTH);
      
      pnlGameStatus = new JPanel();
      pnlGameStatus.setLayout(new GridLayout(1,1));
     
      TitledBorder gameStatusBorder = new TitledBorder("Game Status");
      gameStatusBorder.setTitleJustification(TitledBorder.LEFT);
      gameStatusBorder.setTitlePosition(TitledBorder.TOP);
      pnlGameStatus.setBorder(gameStatusBorder);
      this.add(pnlGameStatus, BorderLayout.WEST);
      
      pnlPlayArea = new JPanel();
      pnlPlayArea.setLayout(new GridLayout(numPlayers, numPlayers));
      TitledBorder playBorder = new TitledBorder("Last Played Cards");
      playBorder.setTitleJustification(TitledBorder.LEFT);
      playBorder.setTitlePosition(TitledBorder.TOP);
      pnlPlayArea.setBorder(playBorder);
      this.add(pnlPlayArea, BorderLayout.CENTER);
      
      pnlHumanPot = new JPanel();
      pnlHumanPot.setLayout(new GridLayout(2,numCardsPerHand/2));
      TitledBorder humanPotBorder = new TitledBorder("Your Winnings");
      humanPotBorder.setTitleJustification(TitledBorder.LEFT);
      humanPotBorder.setTitlePosition(TitledBorder.TOP);
      pnlHumanPot.setBorder(humanPotBorder);
      this.add(pnlHumanPot, BorderLayout.EAST);
      
      pnlHumanHand = new JPanel();
      pnlHumanHand.setLayout(new GridLayout(1,numCardsPerHand));
      TitledBorder humanBorder = new TitledBorder("You");
      humanBorder.setTitleJustification(TitledBorder.LEFT);
      humanBorder.setTitlePosition(TitledBorder.TOP);
      pnlHumanHand.setBorder(humanBorder);
      this.add(pnlHumanHand, BorderLayout.SOUTH);
   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumPlayers()
   {
      return numPlayers;
   }

}
