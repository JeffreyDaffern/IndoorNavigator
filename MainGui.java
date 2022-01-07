package navigator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import edu.princeton.cs.algs4.MinPQ;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

/**
 * Creates the Graphical User Interface(GUI) for the 
 * program.
 * 
 * @author Jeffrey Daffern + Yifu Hou 
 *
 */
public class MainGui extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, navigationWindow, infoPanel;
	private JTextField buildintTxt;
	private JTextField destinationTxt;
	private Color menuBackground = new Color(19, 19, 27);
	private JTextField currentRoomNumTxt;
	private NavigatorApp navigator;
	private JLabel lblAccessPoint1, lblAccessPoint2, lblAccessPoint3, lblBssid1, lblBssid2, lblBssid3,
			lblSignalStrength1, lblSignalStrength2, lblSignalStrength3;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainGui frame = new MainGui();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 */
	public MainGui()
	{
		ClassLoader cl = this.getClass().getClassLoader();
		navigator = new NavigatorApp();

		setTitle("Indoor Navigator");
		setIconImage(new ImageIcon(cl.getResource("navigator/resources/titleIcon.jpg")).getImage());
		setBackground(menuBackground);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);

		createInfoPanel();
		createNavigationWindow();
		navigator.getBuilding().updateSignalStrength(navigator.getUser().getCurrentLocation().getCurrentRoom());
		updateAccessPoints();
	}

	private void createNavigationWindow()
	{
		navigationWindow = new NavigatorWindow(navigator.getBuilding(), navigator.getUser());
		navigationWindow.setBackground(Color.WHITE);
		navigationWindow.setPreferredSize(new Dimension(875, 600));
		navigationWindow.setLayout(null);

		for (JLabel label : ((NavigatorWindow) navigationWindow).getLabels())
		{
			navigationWindow.add(label);
		}
		contentPane.add(navigationWindow, BorderLayout.EAST);
	}

	private void createInfoPanel()
	{
		infoPanel = new JPanel();
		contentPane.add(infoPanel, BorderLayout.WEST);
		infoPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
		infoPanel.setLayout(new BorderLayout(0, 0));
		infoPanel.setPreferredSize(new Dimension(300, 400));

		JPanel selection = new JPanel();
		selection.setPreferredSize(new Dimension(300, 175));
		selection.setBorder(new EmptyBorder(0, 5, 5, 5));
		selection.setLayout(new GridLayout(4, 2, 10, 20));
		infoPanel.add(selection, BorderLayout.NORTH);

		JLabel floor = new JLabel("Floor");
		selection.add(floor);

		buildintTxt = new JTextField();
		selection.add(buildintTxt);
		buildintTxt.setColumns(10);

		JLabel currentRoomNum = new JLabel("Current Room Number");
		selection.add(currentRoomNum);

		currentRoomNumTxt = new JTextField();
		selection.add(currentRoomNumTxt);
		currentRoomNumTxt.setColumns(10);

		JLabel roomNum = new JLabel("Destination");
		selection.add(roomNum);

		destinationTxt = new JTextField();
		selection.add(destinationTxt);
		destinationTxt.setColumns(10);

		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int currentRoom, destinationRoom;
				Room room;

				if (!destinationTxt.getText().contentEquals(""))
				{
					room = navigator.getBuilding().getRooms()
							.get(navigator.getBuilding().getIndex(Integer.parseInt(destinationTxt.getText())));
					destinationRoom = Integer.parseInt(destinationTxt.getText());
				} else
				{
					room = navigator.getBuilding().randomRoom();
					destinationRoom = navigator.getBuilding().randomRoom().getRoomNum();
				}
				if (currentRoomNumTxt.getText() == "")
					currentRoom = navigator.getUser().getCurrentLocation().getCurrentRoom().getRoomNum();
				else
					currentRoom = Integer.parseInt(currentRoomNumTxt.getText());

				navigator.getBuilding().getRoute(currentRoom, destinationRoom);
				navigator.getUser().updateLocation(new Location(room, navigator.getBuilding().getHallway(room)));
				navigator.getBuilding().updateSignalStrength(navigator.getUser().getCurrentLocation().getCurrentRoom());
				updateAccessPoints();
				navigationWindow.repaint();
			}
		});
		selection.add(searchBtn);

		JButton randomBtn = new JButton("Random");
		randomBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int currentRoom, temp = navigator.getUser().getCurrentLocation().getCurrentRoom().getRoomNum(),
						destinationRoom;

				do
				{
					destinationRoom = navigator.getBuilding().randomRoom().getRoomNum();
				} while (destinationRoom == temp);

				Room room = navigator.getBuilding().getRooms().get(navigator.getBuilding().getIndex(destinationRoom));
				currentRoom = Integer.parseInt(currentRoomNumTxt.getText());

				navigator.getBuilding().getRoute(currentRoom, destinationRoom);
				navigator.getUser().updateLocation(new Location(room, navigator.getBuilding().getHallway(room)));
				navigator.getBuilding().updateSignalStrength(navigator.getUser().getCurrentLocation().getCurrentRoom());
				updateAccessPoints();
				navigationWindow.repaint();

			}
		});
		selection.add(randomBtn);

		JPanel display = new JPanel();
		display.setPreferredSize(new Dimension(275, 325));
		display.setBorder(new EmptyBorder(0, 5, 5, 5));
		infoPanel.add(display, BorderLayout.SOUTH);
		display.setLayout(new GridLayout(9, 2, 0, 0));

		lblAccessPoint1 = new JLabel("AccessPoint 1: ");
		display.add(lblAccessPoint1);
		lblBssid1 = new JLabel("BSSID: ");
		display.add(lblBssid1);
		lblSignalStrength1 = new JLabel("Signal Strength: ");
		display.add(lblSignalStrength1);

		lblAccessPoint2 = new JLabel("AccessPoint 2: ");
		display.add(lblAccessPoint2);
		lblBssid2 = new JLabel("BSSID: ");
		display.add(lblBssid2);
		lblSignalStrength2 = new JLabel("Signal Strength: ");
		display.add(lblSignalStrength2);

		lblAccessPoint3 = new JLabel("AccessPoint 3: ");
		display.add(lblAccessPoint3);
		lblBssid3 = new JLabel("BSSID: ");
		display.add(lblBssid3);
		lblSignalStrength3 = new JLabel("Signal Strength: ");
		display.add(lblSignalStrength3);
	}
	
	/**
	 * Updates an Access Point.
	 */
	public void updateAccessPoints() {

		MinPQ<Room> rooms = navigator.getBuilding()
				.getNearestAccessPoints(navigator.getUser().getCurrentLocation().getCurrentRoom());
		Room room = rooms.delMin();
		System.out.println(room);
		lblAccessPoint1.setText("AccessPoint 1: " + room.getRoomNum());
		lblBssid1.setText("BSSID: " + room.getAccessPoint().getBSSID());
		lblSignalStrength1.setText("Signal Strength: " + room.getAccessPoint().getSignalStrength());

		room = rooms.delMin();
		System.out.println(room);

		lblAccessPoint2.setText("AccessPoint 2: " + room.getRoomNum());
		lblBssid2.setText("BSSID: " + room.getAccessPoint().getBSSID());
		lblSignalStrength2.setText("Signal Strength: " + room.getAccessPoint().getSignalStrength());

		room = rooms.delMin();
		System.out.println(room);
		lblAccessPoint3.setText("AccessPoint 3: " + room.getRoomNum());
		lblBssid3.setText("BSSID: " + room.getAccessPoint().getBSSID());
		lblSignalStrength3.setText("Signal Strength: " + room.getAccessPoint().getSignalStrength());
	}
}
