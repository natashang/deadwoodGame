
/*
 * Team: Carroll, Cruz, Ng, Yung
 * CSCI 345: Deadwood Game Assignment
 * Summer 2017 
 * 
 * File: Stage.java
 * Libraries used: java.util (.ArrayList, .Random)
 * 				   org.w3c.dom (.Element, .NodeList)
 * 
 * Purpose: Maintains attributes and behaviors of a Stage object
 * 			Calls Card, Role classes and extends Room class
 * 			Called in Deadwood, Player, Room classes 
 * 
 */

import java.util.*;
import org.w3c.dom.*;
import javax.swing.*;
import java.awt.*;

public class Stage extends Room {

	// Attributes
	private int n_Shot = 0;
	private int maxShot;
	private Card scene = null;
	private ArrayList<Role> roles = null;
	
	private int x;
	private int y;

	private JLabel image;
	private JLabel cardBack;
	private ArrayList<JLabel> shotImages;

	// Constructor
	public Stage(Element e) {
		super(e.getAttribute("name"));
		this.setMaxShot(e);
		this.setOffRolls(e);
		this.parseX(e);
		this.parseY(e);
		this.x = x;
		this.y = y;
		shotImages = new ArrayList<JLabel>();
	}

	public static Stage build(Element e) {
		Stage s = new Stage(e);
		return s;
	}

	public void addShotCounter(JLabel image){		
		shotImages.add(image);		
	}		
	public ArrayList<JLabel> getShotImages(){		
		return shotImages;		
	}		
	public void setCardBack(JLabel image){		
		this.cardBack = image;		
	}		
	public int getX(){		
		return x;		
	}		
	public int getY(){		
		return y;		
	}		
	public JLabel getCardBack(){		
		return cardBack;		
	}		
		public void setImage(JLabel image){		
			this.image = image;		
		}		
		public JLabel getImage(){		
			return image;		
		}		

	
	// Gets the number of shots a scene has
	public int getNumShots() {
		return n_Shot;
	}

	// Gets the maximum number of shots
	public int getMaxShot() {
		return maxShot;
	}

	// Gets scene
	public Card getScene() {
		return scene;
	}

	// Gets an ArrayList of roles off the card (and on the game board)
	public ArrayList<Role> getOffRoles() {
		return roles;
	}

	// Sets the number of shots
	void setNumShots(int shots) {
		this.n_Shot = shots;
	}

	// Sets the scene
	public void setScene(ArrayList<Card> cards) {
		Random rand = new Random();
		int temp = rand.nextInt(cards.size() - 1);
		while (true) {
			temp = rand.nextInt(cards.size() - 1);
			if (!cards.get(temp).get_is_used()) {
				this.scene = cards.get(temp);
				cards.get(temp).set_is_used(true);
				break;
			}
		}
	}

	// Sets the maximum number of shots
	public void setMaxShot(Element e) {
		NodeList take = e.getElementsByTagName("take");
		this.n_Shot = this.maxShot = take.getLength();
	}

	// Sets off rolls
	public void setOffRolls(Element e) {
		this.roles = new ArrayList<Role>();
		NodeList onRoles = e.getElementsByTagName("part");
		for (int i = 0; i < onRoles.getLength(); i++) {
			Role r = new Starring();
			this.roles.add((Role) (r.build((Element) onRoles.item(i))));
		}
	}
	
	public void parseX(Element e){		
    NodeList area = e.getElementsByTagName("area");		
    Element a = (Element) area.item(0);		
    this.x = Integer.parseInt(a.getAttribute("x"));		
 }		
 		
  public void parseY(Element e){		
    NodeList area = e.getElementsByTagName("area");		
    Element a = (Element) area.item(0);		
    this.y = Integer.parseInt(a.getAttribute("y"));		
 }

}