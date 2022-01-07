package navigator;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author Jeffrey Daffern + Yifu Hou
 * 
 *         Creates a type of Room which stores room numbers and the BSSID of the
 *         Access Point located in the room as well as its dimension and bottom
 *         right point for generating the visual representation.
 * 
 */
public class Room implements Comparable<Room> {
	private int roomNum;
	private Rectangle dimension;
	private AccessPoint accessPoint;
	private Point bottomRightPoint;
	private boolean isCurrentRoom;

	/**
	 * Creates a empty room.
	 */
	public Room() {

	}

	/**
	 * Creates and initiates a type of Room.
	 * 
	 * @param roomNum
	 * @param dimension
	 * @param bottomRightPoint
	 * @param accessPoint
	 */
	public Room(int roomNum, Rectangle dimension, Point bottomRightPoint, AccessPoint accessPoint) {
		this.roomNum = roomNum;
		this.dimension = dimension;
		this.accessPoint = accessPoint;
		this.bottomRightPoint = bottomRightPoint;
		this.isCurrentRoom = false;
	}

	/**
	 * Accesses room number.
	 * 
	 * @return a room number.
	 */
	public int getRoomNum() {
		return roomNum;
	}

	/**
	 * Accesses the dimension of a room
	 * 
	 * @return a Rectangle
	 */
	public Rectangle getDimension() {
		return dimension;
	}

	/**
	 * Accesses bottom right points of rectangles that represent rooms.
	 * 
	 * @return bottom right point of rectangles.
	 */
	public Point getBottomRightPoint() {
		return bottomRightPoint;
	}

	/**
	 * Accesses Access Points. represent an Access Point
	 * 
	 * @return bottom right point of rectangles.
	 */
	public AccessPoint getAccessPoint() {
		return accessPoint;
	}

	/**
	 * Determines if a room is defined as Hallway by room number.
	 * 
	 * @return true is a Hallway false is not a Hallway
	 */
	public boolean isHallway() {
		if (roomNum == 290 || roomNum == 288 || roomNum == 287 || roomNum == 285)
			return true;
		return false;
	}

	/**
	 * Determines if a room is the current room.
	 * 
	 * 
	 * @return true is the current room false is not a current room
	 */
	public boolean isCurrentRoom() {
		return isCurrentRoom;
	}

	/**
	 * Sets a room as the current room.
	 * 
	 * @param current
	 */
	public void setCurrentRoom(boolean current) {
		isCurrentRoom = current;
	}

	/**
	 * Return a String representation of this room.
	 * 
	 * @return a String representation of this room.
	 */
	@Override
	public String toString() {
		return "Room [roomNum=" + roomNum + ", dimension=" + dimension + ", accessPoint=" + accessPoint + "]";
	}

	/**
	 * Compares rooms by signal strength of Access Points.
	 * 
	 * @return 1 if this room has stronger signal strength than the other room has 0
	 *         if both rooms have the same signal strength -1 if this room has
	 *         weaker signal strength than the other room has
	 */
	@Override
	public int compareTo(Room other) {
		if (this.accessPoint.getSignalStrength() < other.accessPoint.getSignalStrength())
			return 1;
		else if (this.accessPoint.getSignalStrength() > other.accessPoint.getSignalStrength())
			return -1;
		else if (this.roomNum < other.roomNum)
			return 1;
		else if (this.roomNum > other.roomNum)
			return -1;
		else
			return 0;
	}
}
