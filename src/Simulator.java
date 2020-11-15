//name: Kevin Mathew
//id number: 112167040
//recitation: 02
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.LinkedList;

 /**
  * Simulator Class follows the procedures for the simulator
  * @author Kevin
  *	
  */
public class Simulator {
	private Router dispatcher;
	private LinkedList<Router> routers;
	private LinkedList<Router> routerQueue; //when delivering 
	private int totalServiceTime;
	private int packetsDropped;
	private double arrivalProb;
	private int numIntRouters;
	private int maxBufferSize;	//max router queue space
	private int minPacketSize;
	private int maxPacketSize;
	private int bandWidth;		//amount of delivered packets in one simulation time
	private int duration;
	private int packetsServed;
	
	public static int MAX_PACKETS = 3; 
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public Simulator() {
		
	}
	
	/**
	 * 
	 * @param numIntRouters
	 * Number of intermediate routers
	 * @param arrivalProb
	 * determines probability of a router coming
	 * @param maxBufferSize
	 * determines maxBufferSize for router
	 * @param minPacketSize
	 * minimum Packet Size
	 * @param maxPacketSize
	 * maximum Packet Size
	 * @param bandWidth
	 * determines amount of max packets sent to destination within one time unit
	 * @param duration
	 * the time units
	 */
	public Simulator(int numIntRouters, double arrivalProb, int maxBufferSize, int minPacketSize, int maxPacketSize, int bandWidth, int duration)
	{
		this.numIntRouters = numIntRouters;
		this.arrivalProb = arrivalProb;
		this.maxBufferSize = maxBufferSize; 
		this.minPacketSize = minPacketSize;
		this.maxPacketSize = maxPacketSize;
		this.bandWidth = bandWidth; 
		this.duration = duration;
	}
	
	/**
	 * 
	 * @return
	 * the average service time per packet
	 */
	public double simulate() {//average service time per packet		
		if (packetsServed == 0) {
			return 0; 
		}
			return (double) totalServiceTime/ packetsServed; //packets served 
	}
	
	/**
	 * 
	 * @param minVal
	 * minimum packet size
	 * @param maxVal
	 * maximum packet size
	 * @return
	 * returns a random value between
	 */
	public int randInt(int minVal, int maxVal) {
		return minVal + (int)(Math.random() * (maxVal - minVal)) ;
	}
	
	/**
	 * non-static method to begin simulation
	 */
	public void beginSimulation() {
		int immedPacketCount = 0;
		routers = new LinkedList<Router>();		//since we are using non-static method
		routerQueue = new LinkedList<Router>();	//must initialize here 
		dispatcher = new Router();
		
		for(int i = 0; i < this.numIntRouters; i++) {
			routers.add(new Router());
		}
		
		for (int i = 1; i <= this.duration; i++) {
			System.out.println("Time: " + i);
			immedPacketCount = 0;
			for(int j = 0; j < MAX_PACKETS; j++) {
				if (Math.random() < this.arrivalProb) {
					immedPacketCount++; 
					Packet packet = new Packet(); //generate new packet
					packet.setPacketSize(this.randInt(this.minPacketSize, this.maxPacketSize)); //this.randInt(minVal, maxVal)
					packet.setTimeArrive(i);
					dispatcher.enqueue(packet); //push packet to dispatcher 
					System.out.println("Packet " + packet.getId() + " arrives at dispatcher with size " + packet.getPacketSize());
				}
			}
				if (immedPacketCount == 0) {
					System.out.println("No packets arrived");
				}
				
				//dispatcher --> intermediate routers (find index) send packet to '
				while(dispatcher.size() != 0) {
					boolean maxedRouters = false; 
					int maxedRouter = 0;
					for(int k = 0; k < routers.size(); k++) {
						if(routers.get(k).size() == this.maxBufferSize) {
							maxedRouter++;
						}
					}
					
					if(maxedRouter == this.numIntRouters) {
						maxedRouters = true; 
					}
					
					if(maxedRouters == false) {
						int index = Router.sendPacketTo(routers);
						Packet newPack = dispatcher.dequeue(); 
						routers.get(index).enqueue(newPack);
						System.out.println("Packet " + newPack.getId() + " sent to Router " + Integer.toString(index + 1));
					}
					else {
						Packet removedPack = dispatcher.dequeue(); 
						System.out.println("Network is congested. Packet " + removedPack.getId() + " is dropped");
						this.packetsDropped++;
						break;
					}
				}
			
			//intermediate routers --> destination router		
				for(int l = 0; l < routers.size(); l++) {
					if (routers.get(l).peek() != null) {
						if (routers.get(l).peek().getTimeToDest() == 0) {
							routerQueue.addLast(routers.get(l));
						}
					}
				}
			
				int networkTime = 0;
				for (int m = 0; m < this.bandWidth; m++) {
					if (routerQueue.peek() != null) {
						Router routerExit = routerQueue.pollFirst();
						Packet packetExit = routerExit.dequeue();
						networkTime = (i - packetExit.getTimeArrive());
						totalServiceTime += networkTime;
						packetsServed++;
						System.out.println("Packet " + packetExit.getId() + " has successfully reached it's destination: +" + networkTime);
					}
				}	

			//print full out table
			for(int k = 0; k < routers.size(); k++) {
				System.out.println("R" + Integer.toString(k+1) + ":" + routers.get(k).toString());
			}
				System.out.println();
				

			//decrement timeToDest for all intermeddiate router's top packets
			//for loop "routers" .peek().timeToDest--; .packetSize - 100
			int decrementedTime = 0;
			int decrementedPacket = 0;
			
			for(int k = 0; k < routers.size(); k++) {
				if (routers.get(k).peek() != null) {
					decrementedTime = routers.get(k).peek().getTimeToDest() - 1;
					decrementedPacket = routers.get(k).peek().getPacketSize() - 100; 
					if (decrementedPacket < 100) {
						decrementedPacket = 0; 
					}
					routers.get(k).peek().setTimeToDest(decrementedTime);
					routers.get(k).peek().setPacketSize(decrementedPacket);
				}
			}	
		}
	}
		
	
	/**
	 * non-static method to print results
	 */
	public void printResults() {
		System.out.println("Simulation ending...");
		System.out.println("Total service time: " + this.totalServiceTime);
		System.out.println("Total packets served: " + this.packetsServed);
		System.out.println("Average service time per packet: " + df.format(this.simulate()));
		System.out.println("Total packets dropped: " + this.packetsDropped);
		System.out.println(); 
	}
	
	/**
	 * main static method
	 * @param args
	 * @throws InputMismatchException
	 * checks for wrong inputs for user entered
	 */
	public static void main(String args[]) throws InputMismatchException{
		Scanner stdin = new Scanner(System.in);
		boolean running = true;
		while(running) {
		try {
			System.out.println("Starting simulator....");
			System.out.println("Enter the number of Intermediate routers: ");
			int immedRouters = stdin.nextInt();
			System.out.println("Enter the arrival probability of a packet: ");
			double arrivalProb = stdin.nextDouble();
			System.out.println("Enter the maximum buffer size of a router: ");
			int maxBuffer = stdin.nextInt();
			System.out.println("Enter the minimum size of a packet: ");
			int minSize = stdin.nextInt();
			System.out.println("Enter the maximum size of a packet: ");
			int maxSize = stdin.nextInt();
			System.out.println("Enter the bandwidth size: ");
			int bandwidth = stdin.nextInt();
			System.out.println("Enter the simulation duration: ");
			int simDuration = stdin.nextInt(); 
			System.out.println();
		
		
			Simulator newSimulation = new Simulator(immedRouters, arrivalProb, maxBuffer, minSize, maxSize, bandwidth, simDuration);
			newSimulation.beginSimulation(); 
						
			//results after simulation
			newSimulation.printResults();
			stdin.nextLine(); //clear buffer
			System.out.print("Do you want to try another simulation?  (y/n) : ");
			String attempt = stdin.nextLine();
			System.out.println();
			if (attempt.equals("y")) {
				running = true;
			}
			else if (attempt.equals("n")) {
				running = false;
				System.out.println("Program terminating successfully...");
			}
			
		}
		catch(InputMismatchException error) {
			System.out.println("Wrong input. Please start Simulation again");
			break;
		}	
	}
				stdin.close();
	}
}
