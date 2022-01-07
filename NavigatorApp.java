package navigator;

/**
 * Creates a type of NavigatorApp that accesses both Class User and Class
 * Building.
 * 
 * @author Jeffrey Daffern + Yifu Hou
 *
 */
public class NavigatorApp {
	private User user;
	private Building building;

	public NavigatorApp() {
		building = new Building();
		Room room = building.randomRoom();
		if (!room.isHallway())
			user = new User(new Location(room, building.getHallway(room)));
		else
			user = new User(new Location(room, room));
	}

	/**
	 * Accesses a building.
	 * 
	 * @return
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Accesses a user.
	 * 
	 * @return a User
	 */
	public User getUser() {
		return user;
	}
}
