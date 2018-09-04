package version1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class DnsClient {
	public static void main(String[] args) throws IOException {
		DatagramSocket clientSocket=new DatagramSocket();
		
		Scanner sc=new Scanner(System.in);
		String hostName=null;
		System.out.print("请输入查询主机:");
		hostName=sc.nextLine();
		//input the query name
		
		byte[] data=new byte[200];
		data=hostName.getBytes();
		DatagramPacket sendPacket= new DatagramPacket(data,hostName.length(),InetAddress.getByName("127.0.0.1"),12345);
		clientSocket.send(sendPacket);
		System.out.println("已发送查询请求");
		
		byte[] buf=new byte[200];
		DatagramPacket packet =new DatagramPacket(buf, buf.length);
		clientSocket.receive(packet);
		//receive server's answer
		
		String received=new String(packet.getData(),0,packet.getLength());
		if (received.substring(received.length()-1,received .length()).equals("/")){
			//to check if we get the right resolved ip
			System.out.println(hostName+"的查询结果如下：");
			String [] str0 =received.split("/");
			for (String s :str0) {
				System.out.println(s);
			}
		}
		else {
			//hostname query is forbidden
			System.out.println(received);
		}
		
	}

}
