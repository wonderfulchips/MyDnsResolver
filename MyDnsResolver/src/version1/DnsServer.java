package version1;
import java.io.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsServer {
	public static boolean running=true;
	public static void main(String[] args) throws IOException {
		DatagramSocket serverSocket= new DatagramSocket(12345);
		//create serversocket on port 12345, using UDP protocol
		
		byte buf[]= new byte [200];
		DatagramPacket receivePacket = new DatagramPacket(buf,buf.length);
		System.out.println("DNSServer is started, ready for query...");
		while(running) {
			System.out.println("-----------------------------------------------");
			serverSocket.receive(receivePacket);
			String str=new String(receivePacket.getData(),0,receivePacket.getLength());
			if(!str.equalsIgnoreCase(".shutdown")) {
				System.out.println("Inquire the ip address of "+str+":");
				//receive client's DNS query		
				byte[] data=new byte[200];
				
				try {
					InetAddress[] addresses = InetAddress .getAllByName(str); 			
					//ʹ��java�Դ��Ľӿں�����ý������ip
					String answer=new String();
					for (InetAddress addr : addresses) { 
						System.out.println(addr.getHostAddress()); 
						answer=addr.getHostAddress()+"/"+answer;
					} 
					//use array for a host may have multiple ip address 
					
					if(testForbidden(str)==false) {
						data=answer.getBytes();
						DatagramPacket sendPacket= new DatagramPacket(data,answer.length(),receivePacket.getAddress(),receivePacket.getPort());
						serverSocket.send(sendPacket);
						System.out.print("�ѷ��ͻظ�");
					}
					else {
						String noAnswer ="not found!";//�����ַ����ݿͻ���ֻ���յ�ǰ������
						data=noAnswer.getBytes();
						DatagramPacket sendPacket= new DatagramPacket(data,noAnswer.length(),receivePacket.getAddress(),receivePacket.getPort());
						serverSocket.send(sendPacket);
						System.out.println("client����"+str+"��ipѶϢ�ѱ�����");
					}
				} catch (Exception e) {
					//���getallbyname()�޷����������������ʧ��
					System.out.println("��������ʧ��!");
					String noAnswer ="please input the correct domain name��";//�����ַ����ݿͻ���ֻ���յ�ǰ������
					data=noAnswer.getBytes();
					DatagramPacket sendPacket= new DatagramPacket(data,noAnswer.length(),receivePacket.getAddress(),receivePacket.getPort());
					serverSocket.send(sendPacket);
				}
			}
			else {
				running =false;
				serverSocket .close();
				System.out.println("�������رա�");
			}
		}
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
				line = br.readLine(); // һ�ζ���һ������			
			}
			}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
