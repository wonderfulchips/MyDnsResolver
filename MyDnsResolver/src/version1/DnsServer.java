package version1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsServer {
	public static void main(String[] args) throws IOException {
		DatagramSocket serverSocket= new DatagramSocket(12345);
		//create serversocket on port 12345, using UDP protocol
		
		byte buf[]= new byte [100];
		DatagramPacket receivePacket = new DatagramPacket(buf,buf.length);
		System.out.println("DNSServer is started, ready for query...");
		System.out.println("-----------------------------------------------");
		while(true) {
			serverSocket.receive(receivePacket);
			String str=new String(receivePacket.getData(),0,receivePacket.getLength());
			System.out.println("Inquire the ip address of "+str+":");
			//receive client's DNS query		
			
			InetAddress[] addresses = InetAddress .getAllByName(str); 
			//System.out.println(addresses.length); 
			String answer=new String();
			for (InetAddress addr : addresses) { 
				System.out.println(addr.getHostAddress()); 
				answer=answer+"/"+addr.getHostAddress();
			} 
			//use array for a host may have multiple ip address 
			
			byte[] data=new byte[100];
			data=answer.getBytes();
			DatagramPacket sendPacket= new DatagramPacket(data,answer.length(),receivePacket.getAddress(),receivePacket.getPort());
			serverSocket.send(sendPacket);
			System.out.print("ÒÑ·¢ËÍ»Ø¸´");

		}
	}

}
