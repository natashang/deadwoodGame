
/*
 * Team: Carroll, Cruz, Ng, Yung
 * CSCI 345: Deadwood Game Assignment
 * Summer 2017
 *
 * File: Deadwood.java
 * Libraries used: org.w3c.dom (.Document, .Element, .Node., .NodeList)
 * 				   java.util (.ArrayList, .Iterator, .Scanner)
 * 				   javax.xml.parsers (.DocumentBuilder, .DocumentBuilderFactory)
 *
 * Purpose: 
 *
 */

import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.swing.*;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.WindowEvent;

public class Deadwood {

	public static int numSceneCardsOnBoard;

	/*
	 * Main function Precondition: gather information about how many players
	 * Postcondition: calls playGame method for game functionality
	 */

	public static void main(String[] args) throws Throwable {

		// String input;
		// int num;

		/*
		 * Scanner requests user input for number of players Precondition: input is a
		 * number within the range of minimum to maximum number of players
		 * Postcondition: the number of players is used to start the game for a specific
		 * number of days
		 */

		// Scanner in = new Scanner(System.in);

		int n_player = View.numPlayer();
		Board board = new Board();

		/*
		 * Initializes the starting rank and credit for each player Starts the game for
		 * a specific number of days (rounds) that will take place
		 */

		int startRank = 1;
		int startCredit = 0;
		Player[] players = new Player[n_player];

		for (int i = 0; i < n_player; i++) {
			players[i] = new Player(i, startRank, startCredit);

		}

		board.setVisible(true);
		playGame(players, getHowManyDays(n_player), board);

	}

	/*
	 * Starts and maintains the game until it ends on the last day Number of days is
	 * dependent on number of players Reads user input and acts accordingly
	 */

	private static void playGame(Player[] players, int maxDays, Board b) throws Throwable {

		ArrayList<Card> cards = init_cards();
		ArrayList<Room> rooms = init_rooms();
		Room startPlace = findRoom(rooms, "Trailer");
		int numPlayers = players.length;

		boolean roleTaken;
		boolean moved;
		boolean worked;
		int currPlayer;
		int counter;

		String command;
		String dest;
		// String[] up;

		// Keeps the game running through the final day

		for (int day = 1; day <= maxDays; day++) {
			View.announce("DAY " + day);
			//
			// Sets the starting place on each day
			for (int k = 0; k < numPlayers; k++) {
				players[k].setRoom(startPlace);
			}

			// Sets the cards for each of the rooms
			Iterator<Room> iter = rooms.iterator();
			while (iter.hasNext()) {

				Object temp = iter.next();
				if (temp.getClass().getSimpleName().toString().equals("Stage")) {
					((Stage) temp).setScene(cards);
					b.addCard(((Stage) temp),((Stage)temp).getScene().getNumOfScene());
					b.addCardBack(((Stage) temp));

				}

			}

			numSceneCardsOnBoard = 10;
			counter = -1;

			/*
			 * I THINK that if we were to loop through all the rooms and counted the number
			 * of scenes with 0 shots, then we wouldn't have to use the static variable
			 * nSCOB.
			 */

			/*
			 * Keeps the current day looping through player turns Condition: there are more
			 * than 1 scene cards currently on the board
			 */
			boolean check = false;
			while (numSceneCardsOnBoard != 1) {

				counter++;
				currPlayer = counter % numPlayers;
				worked = false;
				moved = false;

				//System.out.print("Give me a command: ");

				// ask for action
				command = null;
				dest = null;
				// up = null;

				if (check == true) {
					b.removeD();
				}
				b.displayStats(players[currPlayer]);
				check = true;

				// Parses user's input and acts according to command given
				while (!"end".equals(command)) {
					command = b.getCommand();

					while (command.equals("")) {
						command = b.getCommand();
					}
					if (command.equals("move")) {

						if (moved == false) {
							dest = View.moveRoom(players[currPlayer].getRoom());

							if (players[currPlayer].move(findRoom(rooms, dest))) {
								moved = true;
								// View.announce("Player has successfully moved to " + dest);
								b.removeD();
								b.displayStats(players[currPlayer]);
							}
						}

						else {
							View.announce("You have already moved this turn");
						}
						// b.displayStats(players[currPlayer]);
						b.clearCommand();

						// break;
					}

					/*
					 * Upgrades the player's current rank Checks if it is a legal upgrade command:
					 * upgrade {$/credits} {level}
					 */

					else if (command.equals("upgrade")) {

						// this needs fixing!!!!!!!!!!!!!!!

						String payType = View.upgradePayType();
						int desiredRank = View.upgradeRank();
						players[currPlayer].upgrade(payType, desiredRank);

						//
						// if (command.length > 1) {
						// up = command[1].split(" ", 2);
						// players[currPlayer].upgrade(up[0], Integer.parseInt(up[1]));
						// }
						//
						// else {
						// System.out.println("Please enter a valid upgrade command");
						// }
						System.out.println("upgrade");
						b.clearCommand();

						break;

					}

					/*
					 * The player takes the indicated role Checks if it is a legal work command:
					 * work {part}
					 */

					else if (command.equals("work")) {
						String r = "";
						roleTaken = false;
						if (worked == false) {
							if (players[currPlayer].getRoom().equals("Trailer")
									|| players[currPlayer].getRoom().equals("Office")) {
								View.announce("Can't work at trailer or office.");
								break;
							} else {
								r = View.workOption((Stage) players[currPlayer].getRoom());
							}

							for (int i = 0; i < numPlayers; i++) {
								if (players[i].getHasRole()) {
									if (players[i].getRole().getName().toString().toLowerCase().equals(r)) {
										View.announce("Another player already has this role.");
										roleTaken = true;
									}
								}
							}
							if (!roleTaken) {
								if (players[currPlayer].work(r)) {
									worked = false;
								}
							}
						} else {
							System.out.println("Please enter a valid work command");
							// if (players[i].getRoom())
						}

						b.clearCommand();

					}

					// The current player rehearses
					else if (command.equals("rehearse")) {

						if (worked == false) {
							if (players[currPlayer].rehearsal()) {
								View.announce("You chose to rehearse");
								worked = false;
							}
						}

						else {
							View.announce("You have already worked this turn.");
						}

						System.out.println("rehearse");
						b.clearCommand();

						break;
					}

					// The current player performs their current role
					else if (command.equals("act")) {
						if (worked == false) {

							//System.out.println(players[currPlayer].getRoom().equals("Trailer"));

							if (players[currPlayer].getRoom().equals("Trailer")
									|| players[currPlayer].getRoom().equals("Office")) {
								View.announce("Can't act at trailer or office.");
								break;
							}

							boolean hadRole = players[currPlayer].getHasRole();
							if (players[currPlayer].act()) {
								worked = true;
								//View.announce("Player has acted!");
							}
							boolean hasRole = players[currPlayer].getHasRole();

							if (hadRole && !hasRole) {
								if (playerOnCard(players, currPlayer)) {
									players[currPlayer].wrap(players);
									View.announce("The scene has wrapped!");

								} else
									players[currPlayer].reset();
							}

						} else {
							View.announce("You have already worked this turn.");
						}

						b.clearCommand();
					}

					// Current player wants to end their turn
					else if (command.equals("end")) {

						// Player is only visiting room, not working on a role
						if (players[currPlayer].getHasRole() == false) {
							View.announce("Player was only visiting the room");
							worked = false;
							moved = false;
						}

						// System.out.println("Changing to the next player\n");
						View.announce("Changing to the next player");
						b.clearCommand();
					}

				}
			}

		}

		// END GAME
		int max = players[0].getScore();
		int maxi = 0;
		for (int a = 0; a < players.length; a++) {

			int score = players[a].getScore();
			View.announce(players[a].getName() + " recieved " + score + " points.");

			if (score > max) {
				max = score;
				maxi = a;
			}

		}

		View.announce("The winner is " + players[maxi].getName() + " with " + max + " points.");
	}

	/*
	 * Finds a room given its name
	 */

	private static Room findRoom(ArrayList<Room> rooms, String str) {
		Iterator<Room> iter = rooms.iterator();
		Room room = null;
		str = str.toLowerCase();

		while (iter.hasNext()) {
			room = iter.next();
			if (room.getroomName().toLowerCase().equals(str))
				break;
		}

		return (Room) room;
	}

	/*
	 * Initializes an ArrayList collection of the rooms for the game board Parses
	 * the board.xml file
	 */

	private static ArrayList<Room> init_rooms() throws Throwable {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("board.xml");
		NodeList children = doc.getDocumentElement().getChildNodes();
		ArrayList<Room> temp = new ArrayList<Room>();

		for (int i = 0; i < children.getLength(); ++i) {
			if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
				temp.add(Room.build((Element) children.item(i)));
		}

		return temp;

	}

	/*
	 * Initializes an ArrayList collection of the cards for the game Parses the
	 * cards.xml file
	 */

	private static ArrayList<Card> init_cards() throws Throwable {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("cards.xml");
		NodeList children = doc.getElementsByTagName("card");
		ArrayList<Card> temp = new ArrayList<Card>();

		for (int i = 0; i < children.getLength(); ++i) {
			Element card = (Element) children.item(i);
			temp.add(new Card(card));
		}

		return temp;
	}

	/*
	 * Gets the number of days the game will run for depending on the number of
	 * players
	 */

	private static int getHowManyDays(int n_player) {

		return (n_player == 2 || n_player == 3) ? 3 : 4;
	}

	/*
	 * Returns if there is a player on the card
	 */

	private static boolean playerOnCard(Player[] player, int ind) {

		Player p = player[ind];

		for (int i = 0; i < player.length; i++) {
			if (p.getonRole() && p.getRoom().getroomName().equals(player[i].getRoom().getroomName())) {
				return true;
			}
		}

		return false;
	}

	/*
	 * Returns if the string input is a number
	 */

	private static boolean isNum(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	// Returns if a number is in the range between a minimum and maximum number
	private static boolean isValidRange(int num, int min, int max) {
		return min <= num && num <= max;
	}
}

