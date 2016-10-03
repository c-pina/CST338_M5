import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class CardTable extends JFrame {

	static int MAX_CARD_PER_HAND = 56;
	static int MAX_PLAYERS = 2;
	
	private int numCardsPerHand;
	private int numPlayers;
	
	public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
	
	
	CardTable(String title, int numCardsPerHand, int numPlayers)
	{
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		pnlHumanHand = new JPanel();
		pnlHumanHand.setLayout(new GridLayout(1,numCardsPerHand));
		this.add(pnlHumanHand, BorderLayout.SOUTH);
		pnlHumanHand.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		pnlComputerHand = new JPanel();
		pnlComputerHand.setLayout(new GridLayout(1,numCardsPerHand));
		pnlComputerHand.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.add(pnlComputerHand, BorderLayout.NORTH);
		
		pnlPlayArea = new JPanel();
		pnlPlayArea.setLayout(new GridLayout(numPlayers, numPlayers));
		pnlPlayArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.add(pnlPlayArea, BorderLayout.CENTER);	
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
