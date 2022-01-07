package navigator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.princeton.cs.algs4.Queue;

/**
 * Creates a type of NavigatorWindow to display visual representation of
 * navigation results.
 * 
 * @author Jeffrey Daffern + Yifu Hou
 */
public class NavigatorWindow extends JPanel {
	private static final long serialVersionUID = 1L;
	private Building building;
	private User user;

	/**
	 * Creates and initiates a type of NavigatorWindow.
	 * 
	 * @param building
	 * @param user
	 */
	public NavigatorWindow(Building building, User user) {
		super();
		this.building = building;
		this.user = user;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintThings(g);
	}

	private void paintThings(Graphics g) {
		building.drawBuilding(g);
		user.drawUser(g);
	}

	/**
	 * Creates and stores labels for room numbers which are displayed in the
	 * navigator window.
	 * 
	 * @return a Queue<JLabel> of labels
	 */
	public Iterable<JLabel> getLabels() {
		List<Room> rooms = (List<Room>) building.getRooms();
		Queue<JLabel> labels = new Queue<JLabel>();
		ImageIcon accessPoint = new ImageIcon("src\\navigator\\resources\\AccessPoint.jpg");

		for (Room room : rooms) {
			int x = (int) room.getDimension().getCenterX() - 10;
			int y = (int) room.getDimension().getCenterY() - 12;

			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(accessPoint);
			imageLabel.setBounds(x, y-13, 25, 20);

			JLabel label = new JLabel(String.valueOf(room.getRoomNum()));
			label.setBounds(x, y, 25, 25);
			label.setForeground(Color.black);

			labels.enqueue(label);
			labels.enqueue(imageLabel);
		}
		return labels;
	}

}
