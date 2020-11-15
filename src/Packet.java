//name: Kevin Mathew
//id number: 112167040
//recitation: 02

/**
 * Class Packet consists of particular variables for each Packet
 * 
 * @author Kevin
 *
 */
public class Packet {
	private static int packetCount = 0;
	private int id;
	private int packetSize;
	private int timeArrive;
	private int timeToDest;

	/**
	 * Constructor for the Packet Class
	 */
	public Packet() {
		packetCount++;
		this.id = packetCount;
	}

	/**
	 * 
	 * @return id - the packets id represented my packet count
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id set the id of the packet
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return packetSize - returns the size of the packet
	 */
	public int getPacketSize() {
		return packetSize;
	}

	/**
	 * 
	 * @param 
	 * packetSize set the packetSize of the packet
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}
	
	/**
	 * 
	 * @return
	 * returns the time arrived
	 */
	public int getTimeArrive() {
		return timeArrive;
	}

	/**
	 * 
	 * @param timeArrive
	 * the time arrived for the packet
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}

	/**
	 * 
	 * @return
	 * returns the time arrived
	 */
	public int getTimeToDest() {
		timeToDest = packetSize / 100;
		return timeToDest;
	}

	/**
	 * 
	 * @param timeToDest
	 * set the time to destination
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}

	/**
	 * toString method for the packet
	 */
	@Override
	public String toString() {
		String packet;
		if (this.getPacketSize() > 999) {
			packet = Integer.toString(this.getPacketSize()).substring(0, 2);
		} else {
			packet = Integer.toString(this.getPacketSize()).substring(0, 1);
		}
		return "[" + this.getId() + ", " + this.getTimeArrive() + ", " + packet + "]";
	}

}
