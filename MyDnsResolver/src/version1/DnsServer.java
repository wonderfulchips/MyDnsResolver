package version1;
import java.io.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsServer {
	public static void main(String[] args) throws IOException {
		DatagramSocket serverSocket= new DatagramSocket(12345);
		//create serversocket on port 12345, using UDP protocol
		
		byte buf[]= new byte [200];
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
				answer=addr.getHostAddress()+"/"+answer;
			} 
			//use array for a host may have multiple ip address 
			
			byte[] data=new byte[200];
			
			if(testForbidden(str)==false) {
				data=answer.getBytes();
				DatagramPacket sendPacket= new DatagramPacket(data,answer.length(),receivePacket.getAddress(),receivePacket.getPort());
				serverSocket.send(sendPacket);
				System.out.print("已发送回复");
			}
			else {
				String noAnswer ="not found!";//中文字符传递客户端只能收到前三个？
				data=noAnswer.getBytes();
				DatagramPacket sendPacket= new DatagramPacket(data,noAnswer.length(),receivePacket.getAddress(),receivePacket.getPort());
				serverSocket.send(sendPacket);
				System.out.println("client查找"+str+"的ip讯息已被屏蔽");
			}
		}
		//serverSocket.close();
	}
	public static boolean testForbidden(String str) {
		boolean flag = false;
		try {
			String pathname = "D:\\learning\\Javademo\\MyDnsResolver\\MyDnsResolver\\src\\version1\\blacklist.txt";
			File filename = new File(pathname); 
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 
			BufferedReader br = new BufferedReader(reader); 
			//open the txt file check if this host is in the blacklist
			String line = null;
			line = br.readLine();
			while (line != null) {
				if(line.equals(str)) {
					flag = true;
				}
				line = br.readLine(); // 一次读入一行数据			
			}
			}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
