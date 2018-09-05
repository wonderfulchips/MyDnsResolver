package version1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class DnsClient {
	public static boolean running=true;
	public static boolean timing=false;
	public static String hostName=null;
	public static DatagramSocket clientSocket;
	public static void main(String[] args) throws IOException {
		clientSocket =new DatagramSocket();
		
		Scanner sc=new Scanner(System.in);
		
		while(running) {
			System.out.print("�������ѯ����:");
			hostName=sc.nextLine();
			//input the query name
			
			if(!(hostName.equalsIgnoreCase(".quit")||hostName.equalsIgnoreCase(".shutdown"))) {
				Thread t1=new Thread(new TimeThread(),"time");
				t1.start();
				timing=false;
				//��ʱ����3����δ���յ���������

				byte[] data=new byte[200];
				data=hostName.getBytes();
				DatagramPacket sendPacket= new DatagramPacket(data,hostName.length(),InetAddress.getByName("127.0.0.1"),12345);
				clientSocket.send(sendPacket);
				System.out.println("�ѷ��Ͳ�ѯ����");				
				
				byte[] buf=new byte[200];
				DatagramPacket packet =new DatagramPacket(buf, buf.length);
				try {
					clientSocket.receive(packet);
					//receive server's answer
					String received=new String(packet.getData(),0,packet.getLength());
					if (received.substring(received.length()-1,received .length()).equals("/")){
						//to check if we get the right resolved ip
						System.out.println(hostName+"�Ĳ�ѯ������£�");
						String [] str0 =received.split("/");
						for (String s :str0) {
							System.out.println(s);
						}
					}
					else {
						//hostname query is forbidden
						System.out.println(received);
					}
					timing=true;
					t1.interrupt();
				} catch (Exception e) {
					// TODO: handle exception
				}				
			}
			else {
				if(hostName.equalsIgnoreCase(".shutdown")) {
					byte[] data=new byte[200];
					data=hostName.getBytes();
					DatagramPacket sendPacket= new DatagramPacket(data,hostName.length(),InetAddress.getByName("127.0.0.1"),12345);
					clientSocket.send(sendPacket);
				}
				quit();
			}
		}
		
	}
	public static void quit() {
		clientSocket.close();
		running =false;
		System.out.println("�ͻ������˳�");
	}
	
	public static class TimeThread implements Runnable {
		public void run() {
			try {
				Thread.sleep(3000);
				if(timing ==false) {
					System.out.println("timeout!");
					quit();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
	}

}
