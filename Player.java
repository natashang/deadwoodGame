/*
 * Team: Carroll, Cruz, Ng, Yung
 * CSCI 345: Deadwood Game Assignment
 * Summer 2017
 *
 * File: Player.java
 *
 * Purpose: Contains all of the information and behaviors for a Deadwood game player
 * 			Called in Deadwood class
 * 			Calls Card, CastingOffice, Deadwood, Role, Room, Stage, Starring classes
 *
 */

import java.util.*;
import java.lang.System;

public class Player {

	// -------------------------- Attributes ------------------------------------

	private String name;
	private int rank;
	private int credits;
	private int dollars = 0;
	private Room room;
	private Role role;
	private int rehearsal = 0;

	private Card scene;
	private boolean hasRole = false;
	private boolean onRole = false;


	// -------------------------- Constructor -----------------------------------

	public Player(int name, int rank, int credits) {
		this.rank = rank;
		this.credits = credits;
		setName(name);
	}

	// ---------------------------- Getters -------------------------------------

	// Gets the name of the player
	public String getName() {
		return name;
	}

	// Gets the player's rank
	public int getRank() {
		return rank;
	}

	// Get the player's credits
	public int getCredits() {
		return credits;
	}

	// Gets the player's amount of money
	public int getDollars() {
		return dollars;
	}

	// Gets the player's room
	public Room getRoom() {
		return room;
	}

	// Gets the player's current role
	public Role getRole() {
		return role;
	}

	// Gets the number of times a player has rehearsed
	public int getRehearsal() {
		return rehearsal;
	}

	// Gets the scene
	public Card getScene() {
		return scene;
	}

	// Gets whether or not a player has a role
	public boolean getHasRole() {
		return hasRole;
	}

	// Gets the player's status as if they are on role or not
	public boolean getonRole() {
		return onRole;
	}

	// Gets the player's score
	public int getScore() {
		return ((rank * 5) + dollars + credits);
	}

	// ---------------------------- Setters -------------------------------------

	// Sets the name
	private void setName(int i) {
		String[] names = { "red", "orange", "yellow", "green", "blue", "purple", "black", "white" };
		this.name = names[i];
	}

	// Sets the player's rank when upgrading
	private void setRank(int newRank) {
		this.rank = newRank;
	}

	// Sets the number of credits
	private void setCredits(int cr) {
		this.credits = cr;
	}

	// Sets the amount of money
	private void setDollars(int dol) {
		this.dollars = dol;
	}

	// Sets the player's current room to a new room
	public void setRoom(Room inRoom) {
		this.room = inRoom;
	}

	// Sets the player to a new role
	private void setRole(Role r) {
		this.role = r;
	}

	// Sets the number of times a player has rehearsed
	private void setRehearsal(int i) {
		this.rehearsal = i;
	}

	// Sets the player's scene
	private void setScene(Card scene) {
		this.scene = scene;
	}

	// Sets whether or not a player has a role
	private void setHasRole(boolean bool) {
		this.hasRole = bool;
	}

	// Sets the player's role
	private void setOnRole(boolean b) {
		this.onRole = b;
	}

	// Increments the number of rehearsals using setRehearsal(int)
	private void addToRehearsal(int i) {
		setRehearsal(getRehearsal() + 1);
	}

	// Changes (add/subtract) the number of credits using setCredits(int) <-- spendCredits
	private void addToCredits(int c) {
		setCredits(getCredits() + c);
	}

	// Changes (add/subtract) amount of money using setDollars(int) <-- spendMoney
	private void addToDollars(int d) {
		setDollars(getDollars() + d);
	}

	// ----------------- Methods depending on user command input ----------------------------

	/*
	 * Identifies the current players and any parts the player is working
	 */
	public void who() {
		View.announce(name + " " + " ($" + dollars + "," + credits + "cr" + ") ");

		if (getRole() != null) {
			View.announce(" working " + getRole().getName() + ", " + getRole().getLine());
		}

		else {
			View.announce("and currently not working any roles");
		}

	}

	/*
	 * Moves a player to a new room Condition: If player is already working on a
	 * role, they cannot move
	 */

	public boolean move(Room dest) {

		boolean canMove = false;

		// Case: the player does not have a role
		if (getHasRole() == false) {

			ArrayList<String> moveOptions = this.getRoom().getAdjacentRooms();

			// Goes through all the possible room options the player can move into
			for (int i = 0; i < moveOptions.size(); i++) {

				// Player wants to move to an adjacent room
				if (moveOptions.get(i).toLowerCase().equals(dest.getroomName().toLowerCase())) {
					this.setRoom(dest);
					canMove = true;
					break;
				}

			}

			// Prints statement of player moving or not
			if (canMove == true){
				View.announce("Player " + this.getName() + " has moved to " + this.getRoom().getroomName());
				return true;
			}
			else{
				View.announce("Player can only move to an adjacent room");
				return false;
			}

		}

		// Player is working so they cannot move
		else {
			View.announce("Player cannot move either because they are currently working");
			return false;
		}

	}

	private int dice() {
		Random dice = new Random();
		int n = dice.nextInt(6) + 1;
		return n;
	}

	/*
	 * Describes the player's current room and roles they are/are not working on
	 */

	public void where() {

		//System.out.print("in ");

		// Case: player is in Casting Office
		if (this.room.getClass().getSimpleName().toString().equals("CastingOffice")) {
			View.announce("in Casting Office");
		}

		// Case: player is in Trailer
		else if (this.room.getClass().getSimpleName().toString().equals("Trailer")) {
			View.announce("in " + this.getRoom().getroomName());
		}

		// Case: player is in another room
		else {

			Stage s = (Stage) getRoom();
			String scene = "";
			Card c;

			// Gathers information about the scene being filmed

			if (s.getScene() != null && s.getScene().get_is_used()) {
				c = (Card) s.getScene();
				scene += "shooting " + c.getName() + " scene " + c.getNumOfScene();

			}

			View.announce("in " + this.getRoom().getroomName() + " " + scene);

		}
	}

	/*
	 * Upgrades a player's rank
	 * Player can upgrade using either money or credits
	 * Checks if player has enough to upgrade
	 */

	public void upgrade(String payType, int desiredRank) {

		int maxRank = 6;

		if (getRoom().getroomName().equals("Office")) {
			CastingOffice c = (CastingOffice) getRoom();

			// Prevents downgrading
			// Highest limit possible is 6

			if (desiredRank <= getRank() || desiredRank > maxRank) {
				setRank(getRank());
				View.announce("Invalid rank selection.");
			}

			else {

				/*
				 * Player wants to upgrade via money
				 * Checks if player has enough money
				 */

				if (payType.equals("$")) {
					int m = c.getDollar(desiredRank);

					if (getDollars() >= m) {
						addToDollars(-m);
						setRank(desiredRank);
					}

					else {
						View.announce("Sorry you don't have enough money.");
						setRank(getRank());
					}
				}

				/*
				 * Player wants to upgrade via credits
				 * Checks if player has enough money
				 */

				else if (payType.equals("cr")) {
					int cr = c.getCredit(desiredRank);

					if (getCredits() >= cr) {
						addToCredits(-cr);
						setRank(desiredRank);
					}

					else {
						View.announce("Sorry you don't have enough credits.");
						setRank(getRank());
					}
				}
			}
		}

		else {
			View.announce("You are not at the casting office!!");
		}
	}

	/*
	 * The current player rehearses
	 * Tracks how many times a player rehearses
	 */

	public boolean rehearsal() {

		if (this.getHasRole()
				&& this.getRehearsal() < Integer.parseInt(((Stage) this.getRoom()).getScene().getBudget()) - 1) {
			this.addToRehearsal(1);
			View.announce("You will have " + getRehearsal() + " added to your next roll.");
			return true;
		}

		else {
			View.announce(
					"You can't rehearse again.\nEither you don't have a Role or you have already rehearsed enough times.");
			return false;
		}

	}

	/*
	 * The current player takes an indicated part Conditions: Player must not
	 * already have a role or be in Trailer or Casting Office
	 */

	public boolean work(String input) {
		Room temp = getRoom();

		// Checks for the two big conditions

		if (!getHasRole() && !temp.getroomName().equals("Trailer") && !temp.getroomName().equals("Office")) {
			Stage stage = (Stage) temp;

			if (stage.getNumShots() == 0) {
				View.announce("That role is not available to you.");
				return false;
			}

			// Gets the role
			Starring[] role = stage.getScene().getStarrings();

			//Show on-card roles
			if (input.toLowerCase().equals("opportunities")){
				View.announce("On-Card roles are:");
				for (int o = 0; o < role.length; o++){
					View.announce(role[o].getName() + ", level: " + role[o].getLevel());
				}
			}
			// Player wants to take a role on the card
			for (int i = 0; i < role.length; i++) {

				if (role[i].getLevel() <= getRank() && role[i].getName().toLowerCase()
						.equals(input.toLowerCase())) {

					this.setScene(stage.getScene());
					this.setRole((Role) role[i]);
					this.setOnRole(true);
					this.setHasRole(true);
					View.announce("Player is now working on a starring role");
					return true;

				}
			}

			// Player wants to take a role on the board (off card)

			if (getRole() == null) {

				Object[] array = stage.getOffRoles().toArray();
				role = new Starring[array.length];

				// role = (Starring[]) stage.getOffRoles().toArray();
				System.arraycopy(array, 0, role, 0, role.length);

				//Show off-card roles
				if (input.toLowerCase().equals("opportunities")){
					View.announce("Off-Card roles are:");
					for (int o = 0; o < role.length; o++){
						View.announce(role[o].getName() + ", level: " + role[o].getLevel());
					}
					return false;
				}

				for (int i = 0; i < role.length; i++) {
					if (role[i].getLevel() <= getRank()
							&& role[i].getName().toLowerCase().equals(input.toLowerCase())) {

						setRole(role[i]);
						setOnRole(false);
						setHasRole(true);
						View.announce("Player is now working as an extra");
						return true;
					}
				}

			}

			View.announce("That role is not available to you.");
			return false;

		}

		else {
			View.announce("The room you are in has no openings available.");
			return false;
		}

	}

	/*
	 * The current player performs their role
	 */

	public boolean act() {

		if (getHasRole() && !getRoom().getroomName().equals("Trailer") && !getRoom().getroomName().equals("Office")) {

			Stage room = (Stage) this.getRoom();
			Card scene = room.getScene();
			int budget = Integer.parseInt(scene.getBudget());
			int points = dice() + this.getRehearsal();
			View.announce("The budget is " + budget + " and you rolled " + points);

			if (points >= budget) {

				// Player is on card
				if (this.getonRole()) {
					addToCredits(2);
					View.announce("Success! Great acting!");
					View.announce("You are awarded 2 credits");
				}

				// Player is off card
				else {
					addToCredits(1);
					addToDollars(1);
					View.announce("Success! Great acting!");
					View.announce("You are awarded $1 and 1 credit");
				}

				int shots = room.getNumShots();
				room.setNumShots(--shots);
				View.announce("A shot marker has been removed");
				View.announce("There are " + shots + " shots left");

				// want to wrap up
				if (shots == 0) {
					scene.set_is_used(false);
					this.setHasRole(false);
					Deadwood.numSceneCardsOnBoard--;
					View.announce("Scene Complete!");
				}
			}

			else {
				View.announce("Failure! Bad acting!");
				if (!this.getonRole()) {
					addToDollars(1);
					View.announce("You are awarded $1");
				}
			}
			return true;
		}

		else {
			View.announce("You are not currently working on a role");
			return false;
		}
	}

	/*
	 * Resets the player's role, scene, and rehearsal information to default not
	 * working on a role
	 */

	public void reset() {
		this.setOnRole(false);
		this.setHasRole(false);
		this.setRehearsal(0);
		this.setRole(null);
		this.setScene(null);
	}

	/*
	 * Wrapping up a scene
	 */
	public void wrap(Player[] player) {

		setHasRole(true);
		int budget = Integer.parseInt(scene.getBudget());
		int[] results = new int[budget];
		Starring[] stars = scene.getStarrings();

		for (int i = 0; i < budget; i++) {
			results[i] = dice();
		}
		results = computeSceneWrap(results, stars.length);
		for (Player p : player) {
			if (p.getHasRole() && p.getRoom().getroomName().equals(this.getRoom().getroomName())) {

				// player is on card
				if (p.getonRole()) {
					for (int i = 0; i < stars.length; i++) {

						// find their starring role if it exists
						if (p.getRole().getName().equals(stars[i].getName())) {
							p.setDollars(p.getDollars() + results[i]);
							View.announce(p.getName() + " has received a bonus of $" + results[i]);
						}
					}
				
				}

				else {
					p.setDollars(p.getDollars() + p.getRole().getLevel());
					View.announce("You do not receive any extra bonuses");
				}
				resetRole(p);
			}
		}
	}

	private int[] computeSceneWrap(int[] array, int num) {
		int[] results = new int[num];
		for (int i = 0; i < array.length; i++) {
			results[i % num] += array[i];
		}
		return results;
	}

	/*
	 * Resets a player's role
	 */
	private void resetRole(Player play) {
		play.setOnRole(false);
		play.setHasRole(false);
		play.setRehearsal(0);
		play.setRole(null);
		play.setScene(null);
	}

}