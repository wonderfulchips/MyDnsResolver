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
		System.out.print("�������ѯ����:");
		hostName=sc.nextLine();
		//input the query name
		
		byte[] data=new byte[100];
		data=hostName.getBytes();
		DatagramPacket sendPacket= new DatagramPacket(data,hostName.length(),InetAddress.getByName("127.0.0.1"),12345);
		clientSocket.send(sendPacket);
		System.out.println("�ѷ��Ͳ�ѯ����");
		
		byte[] buf=new byte[100];
		DatagramPacket packet =new DatagramPacket(buf, buf.length);
		clientSocket.receive(packet);
		//receive server's answer
		
		String received=new String(packet.getData(),0,packet.getLength());
		System.out.println("������£�");
		System.out.println(received);
	}

}
