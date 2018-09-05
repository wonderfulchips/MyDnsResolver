package version1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class DnsClient {
	public static boolean running=true;
	public static void main(String[] args) throws IOException {
		DatagramSocket clientSocket=new DatagramSocket();
		
		Scanner sc=new Scanner(System.in);
		String hostName=null;
		while(running) {
			System.out.print("请输入查询主机:");
			hostName=sc.nextLine();
			//input the query name
			
			if(!(hostName.equalsIgnoreCase(".quit")||hostName.equalsIgnoreCase(".shutdown"))) {
				byte[] data=new byte[200];
				data=hostName.getBytes();
				DatagramPacket sendPacket= new DatagramPacket(data,hostName.length(),InetAddress.getByName("127.0.0.1"),12345);
				clientSocket.send(sendPacket);
				System.out.println("已发送查询请求");
				
				Timer timer=new Timer();
				timer.schedule(new TimerTask() {		
					@Override
					public void run() {
						System.out.println("请求超时，客户端已退出。");
						System .exit(0);
					}
				}, 5000);
				//若在5秒内未接收到回复，则认为超时并且结束这个客户端。
				
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
			else {
				if(hostName.equalsIgnoreCase(".shutdown")) {
					byte[] data=new byte[200];
					data=hostName.getBytes();
					DatagramPacket sendPacket= new DatagramPacket(data,hostName.length(),InetAddress.getByName("127.0.0.1"),12345);
					clientSocket.send(sendPacket);
				}
				clientSocket.close();
				running =false;
				System.out.println("客户端已退出");
			}
		}
		
	}

}
