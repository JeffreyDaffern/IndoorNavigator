package navigator;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Creates a type of AccessPoint.
 * 
 * @author Jeffrey Daffern + Yifu Hou
 * 
 */
public class AccessPoint {
	private String BSSID;
	private int signalStrength;

	/**
	 * Constructs a new Access Point that works as a reference point for navigation.
	 */
	public AccessPoint() {
		this.BSSID = bssidGenerator();
		this.signalStrength = 0;
	}

	/**
	 * Returns BSSID of an Access Point.
	 * 
	 * @return BSSID of an Access Point.
	 */
	public String getBSSID() {
		return BSSID;
	}

	/**
	 * Returns signal strength of an Access Point.
	 * 
	 * @return signal strength of an Access Point.
	 */
	public int getSignalStrength() {
		return signalStrength;
	}

	/**
	 * Sets signal strength of an Access Point.
	 * 
	 * @param signalStrength
	 */
	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	/**
	 * Returns a random BSSID in type String with the format xx:xx:xx:xx:xx:xx.
	 * 
	 * @return a random BSSID in type String
	 */
	public String bssidGenerator() {
		String randomBSSID = "";
		boolean end = false;

		for (int i = 0; i < 6; i++) {
			if (i == 5)
				end = true;

			if (end)
				randomBSSID += StdRandom.uniform(10, 100);
			else
				randomBSSID += StdRandom.uniform(10, 100) + ":";
		}

		return randomBSSID;
	}

	@Override
	public String toString() {
		return "AccessPoint [BSSID=" + BSSID + ", signal strength = " + signalStrength + "]";
	}
}
