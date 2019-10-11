
/*
 * Team: Carroll, Cruz, Ng, Yung
 * CSCI 345: Deadwood Game Assignment
 * Summer 2017
 *
 * File: View.java
 * Libraries used: 
 *
 * Purpose: Used in GUI implementation
 *
 */

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.lang.Object;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class View {

	static ImageIcon[] playerImages;
	static JFrame frame = new JFrame();
	static JPanel buttonPanel = new JPanel();
	static JLayeredPane pane = new JLayeredPane();

	private static class Closer extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	public static int numPlayer() {

		int numPlaying = 0;
		Object[] options = { "2 Players", "3 Players" };
		int n = JOptionPane.showOptionDialog(frame, "Will there be 2 or 3 players?", "Select the number of players",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		frame.pack();
		frame.setVisible(true);

		if (n == JOptionPane.YES_OPTION) {
			numPlaying = 2;
		}

		else if (n == JOptionPane.NO_OPTION) {
			numPlaying = 3;
		}

		return numPlaying;
	}

	// Provides the options for moving to a new room

	public static String moveRoom(Room currRoom) {

		Object[] options = new Object[currRoom.getAdjacentRooms().size()];

		for (int i = 0; i < currRoom.getAdjacentRooms().size(); i++) {
			options[i] = currRoom.getAdjacentRooms().get(i);
		}

		int r = JOptionPane.showOptionDialog(frame, "Which room do you want to move to?", "Select a room to move to",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		frame.pack();
		frame.setVisible(true);

		String newRoom = currRoom.getAdjacentRooms().get(r);
		return newRoom;

	}

	public static String workOption(Stage s){

		String role = "";

		Object[] typeRoles = {"Starring role", "Extra role", "What are the roles?"};

		int q = JOptionPane.showOptionDialog(frame, "Do you want a Starring role, Extra role, or information about the roles?", "Select an option",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, typeRoles, typeRoles[0]);

		if (q == JOptionPane.YES_OPTION) {

			

			JFrame frame2 = new JFrame();
			
			// Gets the role
			Starring[] onCards = s.getScene().getStarrings();

			//Show on-card roles

			View.announce("On-Card roles are:");

			Object[] options = new Object[onCards.length];

			for (int o = 0; o < onCards.length; o++){

				options[o] = onCards[o].getName() + ", level: " + onCards[o].getLevel();
				View.announce(onCards[o].getName() + ", level: " + onCards[o].getLevel());
			}

			/*int star = JOptionPane.showOptionDialog(frame2, "Which role do you want to work on?", "Select a role to work", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (star == JOptionPane.YES_OPTION) {
				role = onCards.
			}

			frame2.pack();
			frame2.setVisible(true);


			announce("chose: " + star);*/
		}
		else if (q == JOptionPane.NO_OPTION){
			Object[] options = new Object[s.getOffRoles().size()];

			for (int i = 0; i < s.getOffRoles().size(); i++) {
				options[i] = s.getOffRoles().get(i).getName();
			}

			int r = JOptionPane.showOptionDialog(frame, "Which role do you want to work on?", "Select a role to work", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			frame.pack();
			frame.setVisible(true);

			role = s.getOffRoles().get(r).getName();    
		}
		else {
			role = "opportunities";
		}


		return role;
	}

	// Creates a pop up window with a message to the user
	public static void announce(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	// Gets which payment type the user wants to use
	public static String upgradePayType() {

		String payType = "";
		Object[] options = { "by $", "by credit" };

		int u = JOptionPane.showOptionDialog(frame, "How do you want to upgrade?", "Select a method of upgrade payment",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		frame.pack();
		frame.setVisible(true);

		if (u == JOptionPane.YES_OPTION) {
			payType = "$";
		} else if (u == JOptionPane.NO_OPTION) {
			payType = "cr";
		}

		return payType;
	}

	// Gets which rank the user wants to upgrade to
	public static int upgradeRank() {

		int rank = 0;
		Object[] options = { "2", "3", "4", "5", "6" };

		int u = JOptionPane.showOptionDialog(frame, "What rank do you want to upgrade to?", "Select your desired rank",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		frame.pack();
		frame.setVisible(true);

		if (u == JOptionPane.YES_OPTION) {
			rank = 2;
		} else if (u == JOptionPane.NO_OPTION) {
			rank = 3;
		}

		return rank;

	}

}
