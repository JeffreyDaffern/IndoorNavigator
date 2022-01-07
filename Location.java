package navigator;

/**
 * Creates a type of Location.
 * 
 * @author Jeffrey Daffern + Yifu Hou
 *
 */
public class Location {
	private Iterable<AccessPoint> nearbyAccessPoints;
	private Room currentRoom, hallway;
	private String BSSID1, BSSID2, BSSID3;
	private Location currentLocation;
	// private ST<roomNum, Point> roomPoints

	/**
	 * Creates and initiates a type of Location.
	 * 
	 * @param currentRoom
	 * @param hallway
	 */
	public Location(Room currentRoom, Room hallway) {
		this.currentRoom = currentRoom;
		this.hallway = hallway;
	}

	/**
	 * Accesses a room from Building class.
	 * 
	 * @param b
	 */
	public void storeRooms(Building b) {
		b.getRooms();
	}

	/**
	 * Accesses a current room.
	 * 
	 * @return a Room as the current room.
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	@Override
	public String toString() {
		return "Location [nearbyAccessPoints=" + nearbyAccessPoints + ", room1=" + currentRoom + ", room2=" + hallway
				+ ", BSSID1=" + BSSID1 + ", BSSID2=" + BSSID2 + ", BSSID3=" + BSSID3 + ", currentLocation="
				+ currentLocation + "]";
	}
}
