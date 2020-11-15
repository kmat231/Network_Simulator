
//name: Kevin Mathew
//id number: 112167040
//recitation: 02
import java.util.LinkedList;

/**
 * 
 * @author Kevin Router class implemented LinkedList API
 */
public class Router extends LinkedList<Packet> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	LinkedList<Packet> packetQueue;

	/**
	 * Constructor intializes a new linkedList of type Packet
	 */
	public Router() {
		packetQueue = new LinkedList<Packet>();
	}

	/**
	 * 
	 * @param p acts as a queue enqueing a packet
	 */
	public void enqueue(Packet p) {
		packetQueue.addLast(p);
	}

	/**
	 * 
	 * @return dequeue packet from Router
	 */
	public Packet dequeue() {
		return packetQueue.pollFirst();
	}

	/**
	 * peek the top of the queue
	 */
	public Packet peek() {
		return packetQueue.peek();
	}

	/**
	 * returns the size of the queue
	 */
	public int size() {
		return packetQueue.size();
	}

	/**
	 * checks if queue is empty
	 */
	public boolean isEmpty() {
		return packetQueue.isEmpty();
	}

	/**
	 * to String method to for each router
	 */
	public String toString() {
		String one = "{";
		String two = "";
		for (int i = 0; i < size(); i++) {
			if (i == size() - 1) {
				two += packetQueue.get(i).toString();
			} else {
				two += packetQueue.get(i).toString() + ",";
			}
		}
		String three = "}";
		return one + two + three;
	}
	/**
	 * 
	 * @param routers
	 * determines which router to send packet to
	 * @return
	 * returns the index of the best fitting router
	 */
	public static int sendPacketTo(LinkedList<Router> routers) {
		int lowestPack = routers.get(0).size();
		int index = 0;

		for (int i = 0; i < routers.size(); i++) {
			if (routers.get(i).size() < lowestPack) {
				lowestPack = routers.get(i).size();
				index = i;
			}
		}
		return index;
	}
}
