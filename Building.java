package navigator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.SymbolGraph;

/**
 * Creates a type of Building.
 * 
 * @author Jeffrey Daffern + Yifu Hou
 *
 */
public class Building {
	private ST<Integer, String> roomByRoomNum;
	private ST<String, Room> roomByBssid;
	private List<Room> rooms;
	private Graph graph;
	private int[] roomNums;
	private Stack<Room> route;

	/*
	 * Creates a Building that holds Rooms Access Points.
	 */
	public Building() {
		String graphFile = "src\\navigator\\resources\\inputBB2.txt";
		String roomFile = "src\\navigator\\resources\\rooms.txt";
		String arrayFile = "src\\navigator\\resources\\roomNums.txt";

		route = new Stack<Room>();
		rooms = new ArrayList<Room>();
		roomByRoomNum = new ST<Integer, String>();
		roomByBssid = new ST<String, Room>();

		In in = new In(graphFile);
		graph = new Graph(in);

		in = new In(roomFile);
		in.readLine();

		while (in.hasNextLine()) {
			int roomNum = in.readInt();
			int x = in.readInt();
			int y = in.readInt();
			int width = in.readInt();
			int height = in.readInt();

			rooms.add(new Room(roomNum, new Rectangle(x, y, width, height), new Point(x + width, y + height),
					new AccessPoint()));
		}

		in = new In(arrayFile);
		int x = 0;
		int arraySize = in.readInt();
		roomNums = new int[arraySize];

		for (int i = 0; i < arraySize; i++) {
			roomNums[x] = in.readInt();
			x++;
		}

		for (int v = 0; v < graph.V(); v++) {
			for (int w : graph.adj(v)) {
				roomByBssid.put(rooms.get(w).getAccessPoint().getBSSID(), rooms.get(w));
				roomByRoomNum.put(roomNums[w], rooms.get(w).getAccessPoint().getBSSID());
			}
		}
	}

	/**
	 * Returns a Room as the result of searching by an Access Point BSSID.
	 * 
	 * @param bssid
	 * @return a Room
	 */
	public Room roomNumLookupByBssid(String bssid) {
		Room room = new Room();
		if (roomByBssid.contains(bssid))
			room = roomByBssid.get(bssid);
		return room;
	}

	/**
	 * 
	 * Returns a BSSID as the result of searching by a room number.
	 *
	 * @param roomNum
	 * @return a BSSID
	 */
	public String bssidLookupByRoomNum(int roomNum) {
		String bssid = "";
		if (roomByRoomNum.contains(roomNum))
			bssid = roomByRoomNum.get(roomNum);
		return bssid;
	}

	/**
	 * Generates and stores the shortest path between the Room with the roomNumber
	 * and the destination room in Stack<Room> route.
	 * 
	 * @param roomNum
	 * @param destination
	 */
	public void getRoute(int roomNum, int destination) {
		BreadthFirstPaths bfPaths = new BreadthFirstPaths(graph, getIndex(destination));
		Iterable<Integer> routeIndex = bfPaths.pathTo(getIndex(roomNum));

		for (Integer room : routeIndex) {
			route.push(rooms.get(room));
		}
	}

	/**
	 * Identifies all Access Points locate within 3 (0-2) steps from a room.
	 * 
	 * @param room
	 * @return a MinPQ<Room> with nearby Access Points.
	 */
	public MinPQ<Room> getNearestAccessPoints(Room room) {
		MinPQ<Room> accessPointRooms = new MinPQ<Room>();

		for (int v = 0; v < graph.V(); v++) {
			BreadthFirstPaths bfPaths = new BreadthFirstPaths(graph, getIndex(room.getRoomNum()));
			if (bfPaths.distTo(v) < 3) {
				accessPointRooms.insert(rooms.get(v));
			}
		}
		return accessPointRooms;
	}

	/**
	 * Determines signal strength by steps away from a room.
	 */
	public void updateSignalStrength(Room room) {
		for (int v = 0; v < graph.V(); v++) {
			BreadthFirstPaths bfPaths = new BreadthFirstPaths(graph, getIndex(room.getRoomNum()));
			System.out.println("v:" + v + " bfPaths.distTo(v) " + bfPaths.distTo(v) + " room:" + room.getRoomNum());
			switch (bfPaths.distTo(v)) {
			case 0:
				rooms.get(v).getAccessPoint().setSignalStrength(100);
				break;
			case 1:
				rooms.get(v).getAccessPoint().setSignalStrength(80);
				break;
			case 2:
				rooms.get(v).getAccessPoint().setSignalStrength(60);
				break;
			default:
				rooms.get(v).getAccessPoint().setSignalStrength(30);
			}
		}
	}

	/**
	 * Converts a room number to an index.
	 * 
	 * @param roomNum
	 * @return index of a room number.
	 */
	public int getIndex(int roomNum) {
		int index = 0;
		for (int i = 0; i < roomNums.length; i++) {
			if (roomNums[i] == roomNum)
				index = i;
		}
		return index;
	}

	/**
	 * Accesses rooms in the List<Room>.
	 * 
	 * @return a List of Rooms
	 */
	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * Generates a random room.
	 * 
	 * @return a random room
	 */
	public Room randomRoom() {
		return rooms.get(StdRandom.uniform(rooms.size()));
	}

	/**
	 * Identifies the hallway right next to a room
	 * 
	 * @param room
	 * @return a hallway defined as a room
	 */
	public Room getHallway(Room room) {
		Bag<Integer> roomNums = (Bag<Integer>) graph.adj(getIndex(room.getRoomNum()));
		int roomNum = 0;

		for (Integer i : roomNums)
			roomNum = i;

		return rooms.get(roomNum);
	}

	/**
	 * Draws a room and colors the current room.
	 * 
	 * @param g
	 */
	public void drawBuilding(Graphics g) {
		for (Room room : rooms) {
			int x = room.getDimension().x;
			int y = room.getDimension().y;
			int width = room.getDimension().width;
			int height = room.getDimension().height;

			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);

			if (room.isCurrentRoom()) {
				g.setColor(new Color(0, 230, 73));
			} else {
				if (room.isHallway())
					g.setColor(new Color(103, 117, 138));
				else
					g.setColor(new Color(74, 84, 99));
			}

			g.clearRect(x, y, width, height);
			g.fillRect(x, y, width, height);
		}

		drawRoute(g);
	}

	/**
	 * Draws a route from one room to another.
	 * 
	 * @param g
	 */
	public void drawRoute(Graphics g) {
		while (!route.isEmpty()) {
			Room room = route.pop();
			int x = room.getDimension().x + 1;
			int y = room.getDimension().y + 1;
			int width = room.getDimension().width - 1;
			int height = room.getDimension().height - 1;

			if (!room.isCurrentRoom()) {
				g.setColor(new Color(247, 35, 45));
				g.clearRect(x, y, width, height);
				g.fillRect(x, y, width, height);
			}
		}
	}

	public static void main(String args[]) {
		String fileName = "src\\navigator\\resources\\symbolGraph.txt";
		In in = new In(fileName);
		SymbolGraph sg = new SymbolGraph(fileName, " ");
		StdOut.print("\nIndex | RoomNum \n");
		StdOut.println("=================");

		for (int i = 0; i < sg.graph().V(); i++) {
			StdOut.printf("%4d  |   ", i);
			StdOut.println(sg.nameOf(i));
		}
		StdOut.println();

		StdOut.print("\nRoomNum | Index \n");
		StdOut.println("=================");
		List<String> list = new ArrayList<>();
		while (in.hasNextLine()) {
			String[] a = in.readLine().split(" ");
			for (int i = 0; i < a.length; i++) {
				if (!list.contains(a[i])) {
					list.add(a[i]);
				}
			}
		}
		for (String s : list) {
			StdOut.printf("%5s   |  %2d%n", s, sg.indexOf(s));
		}
	}
}
