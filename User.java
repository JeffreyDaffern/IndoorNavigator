package navigator;

import java.awt.Color;
import java.awt.Graphics;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Creates a type of User.
 * 
 * @author Jeffrey Daffern + Yifu Hou
 *
 */
public class User
{
	private Location currentLocation;
	// private Location destination;
	// private AccessPoint nearestAccessPoint;
	private int x, y;

	/**
	 * Initializes a new user with the current location.
	 * 
	 * @param currentLocation
	 */
	public User(Location currentLocation)
	{
		this.currentLocation = currentLocation;
		currentLocation.getCurrentRoom().setCurrentRoom(true);
		updateXY(currentLocation);
	}

	/**
	 * Accesses current location.
	 * @return current location
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	private void updateXY(Location currentLocation)
	{
		int minX, maxX, minY, maxY;

		minX = (int) currentLocation.getCurrentRoom().getDimension().getMinX() + 11;
		maxX = (int) currentLocation.getCurrentRoom().getDimension().getMaxX() - 11;
		minY = (int) currentLocation.getCurrentRoom().getDimension().getMinY() + 11;
		maxY = (int) currentLocation.getCurrentRoom().getDimension().getMaxY() - 11;

		x = StdRandom.uniform(minX, maxX);
		y = StdRandom.uniform(minY, maxY);
	}

	/**
	 * Updates with the current location.
	 * 
	 * @param newLocation
	 */
	public void updateLocation(Location newLocation)
	{
		currentLocation.getCurrentRoom().setCurrentRoom(false);
		this.currentLocation = newLocation;
		currentLocation.getCurrentRoom().setCurrentRoom(true);
		
		updateXY(currentLocation);
	}

	/**
	 * Draws the visual representation of a user.
	 * 
	 * @param g
	 */
	public void drawUser(Graphics g)
	{
		g.setColor(Color.blue);
		g.drawOval(x, y, 10, 10);
		g.fillOval(x, y, 10, 10);
	}
}

