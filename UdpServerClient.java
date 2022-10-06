/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclientserver;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author RetailAdmin
 */
public class UdpServerClient {
    public static void main(String[] args)
    {
       // System.out.println(java.util.HashMap.class.getClassLoader());
       // System.out.println(UdpServerClient.class.getDeclaredFields());
        //System.out.println(UdpServerClient.class.getFields());
        System.out.println(UdpServerClient.class.getDeclaredMethods().length);
        
        
        
   // System.out.println(UdpServerClient.class.getClassLoader());
   // System.out.println(java.net.InetAddress.class.getClassLoader());
        DatagramSocket udpSocket = null;
        BufferedReader br = null;
        
        try
        {
            
            //InetAddress me = InetAddress.getLocalHost();
           // System.out.println(me);
            //Create a  UDP Socket at localhost using an availbale port
            udpSocket = new DatagramSocket();
            String msg = null;
            //udpSocket2=DatagramSocket.class.cast(udpSocket);
        
            //Create a buffered reader to get an input form the user
            br = new BufferedReader( new InputStreamReader(System.in));
            
            String promptMessage = "Please enter a message (Bye to quit):";
            System.out.println(promptMessage);
            
            while((msg = br.readLine()) !=null)
            {
                if(msg.equalsIgnoreCase("bye"))
                {
                    break;
                }
                
                //Prepare a packet to send to the server
                DatagramPacket packet = UdpServerClient.getPacket(msg);
                
                //Send the packet to the Server
                udpSocket.send(packet);
                
                //Wait for a packet from the server
                udpSocket.receive(packet);
                
                //Diaplay packet details received from the server
                displayPacketDetails(packet);
                
                System.out.println(promptMessage);
            }
        }
        catch(Exception e){ e.printStackTrace();}
        finally{
            //close the socket
            if(udpSocket != null)
            {
                udpSocket.close();
            }
        }
    }
    
     public static void displayPacketDetails(DatagramPacket packet)
        {
            byte[] msgBuffer = packet.getData();
            int length = packet.getLength();
            int offset = packet.getOffset();
            
            int remotePort = packet.getPort();
            InetAddress remoteAddr = packet.getAddress();
            String msg = new String(msgBuffer, offset, length);
            System.out.println("Server at IP Address=" + remoteAddr +
                     ", port=" + remotePort + "]: " + msg);
            
            //Add a line break
            System.out.println();
        }

     
      public static DatagramPacket getPacket(String msg) throws UnknownHostException
        {
            //We will send and accept a message of 1024 byte in length.
            // longer messages will be truncated
            final int PACKET_MAX_LENGTH = 1024;
            byte[] msgBuffer = msg.getBytes();
            int length = msgBuffer.length;
            
            if(length > PACKET_MAX_LENGTH)
            {
                length = PACKET_MAX_LENGTH;
            }
           DatagramPacket packet = new DatagramPacket(msgBuffer, length);
           
           //Set the destination address and the port number
           int serverPort = 12900;
          // final String SERVER_NAME = "/192.168.43.136";
          //String ipAddress[] = {"192" "168" "43" "136"};
           byte ipAddr[];
           ipAddr =  new byte[4];
           ipAddr[0]= (byte) 192;
            ipAddr[1]= (byte) 168;
             ipAddr[2]= (byte) 43;
              ipAddr[3]= (byte) 136;
          // InetAddress serverIPAddress = InetAddress.getByName(SERVER_NAME);
          InetAddress serverIPAddress = InetAddress.getByAddress(ipAddr);
           System.out.println(serverIPAddress);
           packet.setAddress(serverIPAddress);
           packet.setPort(serverPort);
           
           return packet;
        }

}
