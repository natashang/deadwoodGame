
/*
 * Team: Carroll, Cruz, Ng, Yung
 * CSCI 345: Deadwood Game Assignment
 * Summer 2017
 *
 * File: Board.java
 * Libraries used: 
 *
 * Purpose: Used in GUI implementation
 *
 */

import java.awt.*;
import javax.swing.*;

//import BoardLayersListener.boardMouseListener;

import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Board extends JFrame{

	//public class Board extends JLayeredPane{

	// Private attributes

	// JLabels
	static JLabel currPlayerLabel = new JLabel("");
	static JLabel levelLabel = new JLabel("");
	static JLabel moneyLabel = new JLabel("");
	static JLabel creditLabel= new JLabel("");
	static JLabel rehearsalLabel= new JLabel("");
	static JLabel roomLabel= new JLabel("");
	static JLabel roleLabel= new JLabel("");


	private JLabel boardLabel;
	JLabel menuLabel;
	JLabel cardLabel;
	JLabel pInfoLabel;
	JLabel shotLabel;
	JLabel cardBack;
	
	// JButtons
	JButton bAct;
	JButton bRehearse;
	JButton bMove;
	JButton bWork;
	JButton bEnd;
	JButton bUpgradeM;
	JButton bUpgradeC;
	JButton bUpgrade;

	// LayeredPane
	static JLayeredPane bPane; 
	static JMenuItem level2;
	static JMenuItem level3;
	static JMenuItem level4;
	static JMenuItem level5;
	static JMenuItem level6;
	static JMenuItem money;
	static JMenuItem credit;


	static String command = "";

	// Constructor

	public Board() {



		// Set the title of the JFrame
		super("Deadwood");

		// Set the exit option for the JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bPane = getLayeredPane();

		// Create the JLayeredPane to hold the display, cards, role dice and buttons

		// Create Deadwood board
		boardLabel = new JLabel();
		ImageIcon icon = new ImageIcon("board.jpg");
		boardLabel.setIcon(icon); 
		boardLabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

		// Add the board to the lower layer
		bPane.add(boardLabel, new Integer(0));

		// Set size of GUI
		setSize(icon.getIconWidth()+200,icon.getIconHeight());

		// Add a scene card to this room
		/*  cardLabel = new JLabel();
       ImageIcon cIcon =  new ImageIcon("01.png");
       cardLabel.setIcon(cIcon); 
       cardLabel.setBounds(20,60,cIcon.getIconWidth(),cIcon.getIconHeight());
       cardLabel.setOpaque(true);*/

		// Add the board to the lower layer
		//bPane.add(cardLabel, new Integer(1));

		// Add a dice to represent a player. 
		// Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
		/*currPlayerLabel = new JLabel();
       ImageIcon pIcon = new ImageIcon("r2.png");
       currPlayerLabel.setIcon(pIcon);
       //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
       currPlayerLabel.setBounds(991,248,46,46);
       bPane.add(currPlayerLabel,new Integer(3));*/

		// Create the Menu for action buttons
		menuLabel = new JLabel("MENU");
		menuLabel.setBounds(icon.getIconWidth()+40,0,100,20);
		bPane.add(menuLabel,new Integer(2));

		// Create Action buttons
		bAct = new JButton("ACT");
		bAct.setBackground(Color.white);
		bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
		bAct.addMouseListener(new boardMouseListener());

		bRehearse = new JButton("REHEARSE");
		bRehearse.setBackground(Color.white);
		bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
		bRehearse.addMouseListener(new boardMouseListener());

		bMove = new JButton("MOVE");
		bMove.setBackground(Color.white);
		bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
		bMove.addMouseListener(new boardMouseListener());

		bWork =	new JButton("WORK");
		bWork.setBackground(Color.white);
		bWork.setBounds(icon.getIconWidth()+10, 120,100, 20);
		bWork.addMouseListener(new boardMouseListener());
		//add(bWork,new Integer(3));

		bUpgrade = new JButton("UPGRADE");
		bUpgrade.setBackground(Color.white);
		bUpgrade.setBounds(icon.getIconWidth()+10, 150, 100, 20);
		bUpgrade.addMouseListener(new boardMouseListener());

		/*bUpgradeM =	new JButton("$ UPGRADE");
       bUpgradeM.setBackground(Color.white);
       bUpgradeM.setBounds(icon.getIconWidth()+10, 150, 100,	20);
       bUpgradeM.addMouseListener(new boardMouseListener());

       bUpgradeC =	new JButton("C UPGRADE");
       bUpgradeC.setBackground(Color.white);
       bUpgradeC.setBounds(icon.getIconWidth()+10, 180, 100,	20);
       bUpgradeC.addMouseListener(new boardMouseListener());
       //add(bUpgrade,new Integer(3));
		 */     
		bEnd	= new	JButton("END TURN");
		bEnd.setBackground(Color.white);
		bEnd.setBounds(icon.getIconWidth()+10, 180, 100, 20);   
		bEnd.addMouseListener(new boardMouseListener());
		//add(bEnd,new Integer(3));    

		// Place the action buttons in the top layer
		bPane.add(bAct, new Integer(2));
		bPane.add(bRehearse, new Integer(2));
		bPane.add(bMove, new Integer(2));
		bPane.add(bWork, new Integer(2));
		//bPane.add(bUpgradeM, new Integer(2));
		//bPane.add(bUpgradeC, new Integer(2));
		bPane.add(bUpgrade, new Integer(2));
		bPane.add(bEnd, new Integer(2));

		// Displayer Current Player's info
		pInfoLabel = new JLabel("CURRENT PLAYER INFO");
		pInfoLabel.setBounds(icon.getIconWidth()+10,250,150,20);
		bPane.add(pInfoLabel,new Integer(2));    

	}
	
	public void addCardBack (Stage s) {

		 cardBack = new JLabel();
		 ImageIcon cbIcon = new ImageIcon("cardBack.png");
		 cardBack.setIcon(cbIcon);
		 cardBack.setBounds(s.getX(), s.getY(), 205, 115);
		 bPane.add(cardBack, new Integer(3));
		 cardBack.setOpaque(true);

		}

		public void addCard(Stage s, int scenNum){
		  cardLabel = new JLabel();
		  String card = Integer.toString(scenNum)+".png";
		  ImageIcon cIcon = new ImageIcon(card);
		  cardLabel.setIcon(cIcon);
		  cardLabel.setBounds(s.getX(), s.getY(), 205, 115);
		  bPane.add(cardLabel, new Integer(2));
		  cardLabel.setOpaque(true);
		   
		}

	public static void displayStats(Player player){

		currPlayerLabel.setText("Player: " + player.getName());
		currPlayerLabel.setBounds(1208, 230, 150, 150);
		bPane.add(currPlayerLabel, new Integer(2));

		levelLabel.setText("Level: " + player.getRank());

		//    levelLabel = new JLabel("Level: " + player.getRank());
		levelLabel.setBounds(1208, 265, 150, 150);
		bPane.add(levelLabel, new Integer(2));

		moneyLabel.setText("Money: " + player.getDollars());
		moneyLabel.setBounds(1208, 305, 150, 150);
		bPane.add(moneyLabel, new Integer(2));

		creditLabel.setText("Credit: " + player.getCredits());
		creditLabel.setBounds(1208, 345, 150, 150);
		bPane.add(creditLabel, new Integer(2));

		rehearsalLabel.setText("Rehearsals: " + player.getRehearsal());
		rehearsalLabel.setBounds(1208, 385, 150, 150);
		bPane.add(rehearsalLabel, new Integer(2));

		roomLabel.setText("Room: " + player.getRoom().getroomName());
		roomLabel.setBounds(1208, 425, 150, 150);
		bPane.add(roomLabel, new Integer(2));

		if (player.getHasRole() == true) {
			roleLabel.setText("Role: " + player.getRole().getName());
		}
		else if (player.getHasRole() == false) {
			roleLabel.setText("Role: currently none");
		}
		roleLabel.setBounds(1208, 465, 150, 150);
		bPane.add(roleLabel, new Integer(2));

	}


	public void removeD(){

		bPane.remove(currPlayerLabel);

		bPane.remove(levelLabel);


		bPane.remove(moneyLabel);

		bPane.remove(creditLabel);

		bPane.remove(rehearsalLabel);

	}


	// This class implements Mouse Events

	class boardMouseListener implements MouseListener{

		// Code for the different button clicks
		public void mouseClicked(MouseEvent e) {

			if (e.getSource()== bAct){
				command = "act";
			}
			else if (e.getSource()== bRehearse){
				command = "rehearse";
			}
			else if (e.getSource()== bMove){
				command = "move";
			}
			else if (e.getSource() == bWork){
				command = "work";
			}
			else if (e.getSource() == bUpgradeM){
				command = "upgradeM";
			}
			else if (e.getSource() == bUpgradeC){
				command = "upgradeC";
			}
			else if (e.getSource() == bUpgrade) {
				command = "upgrade";
			}

			else if (e.getSource() == bEnd){
				command = "end";
			}           
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}  

	}

	class LevelListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			// cases for upgrading level
			if (e.getSource() == level2){
				command = "2";
			}
			else if (e.getSource() == level2){
				command = "3";
			}
			else if (e.getSource() == level4){
				command = "4";
			}
			else if (e.getSource() == level5){
				command = "5";
			}
			else if (e.getSource() == level6){
				command = "6";
			}
			else if (e.getSource() == money){
				command = "money";
			}
			else if (e.getSource() == credit){
				command = "credit";
			}
		}

	}
	//    
	//      public void upgradeMenu(){
	// 
	//     JPopupMenu menu = new JPopupMenu("levels");
	//     level2 = new JMenuItem("level 2");
	//     level3 = new JMenuItem("level 3");
	//     level4 = new JMenuItem("level 4");
	//     level5 = new JMenuItem("level 5");
	//     level6 = new JMenuItem("level 6");
	//     menu.add(level2);
	//     menu.add(level3);
	//     menu.add(level4);
	//     menu.add(level5);
	//     menu.add(level6);
	//     level2.addActionListener(new LevelListener());
	//     level3.addActionListener(new LevelListener());
	//     level4.addActionListener(new LevelListener());
	//     level5.addActionListener(new LevelListener());
	//     level6.addActionListener(new LevelListener());
	//     menu.show(bUpgradeM, bUpgradeM.getWidth(), bUpgradeM.getHeight());
	// //     menu.show(bUpgradeC, bUpgradeC.getWidth(), bUpgradeC.getHeight());
	//   }



	public String getCommand(){
		return command;
	}

	public void clearCommand(){
		command = "";
	}


	public static void main(String[] args)  {
		Board board = new Board();
		board.setVisible(true);
		Player p =  new Player(1,2,3);
		displayStats(p);

	}


}