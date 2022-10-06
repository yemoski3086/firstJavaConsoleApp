/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclientserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author RetailAdmin
 */
public class UdpClientServer {

    //DatagramSocket datagramSocket = null; //Represents a UdpSocket that is used to send and receive datagram packet
    DatagramPacket datagramPacket = null; //Represents a unit of transmission over a UDP socket
    
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        try
    {
     final int LOCAL_PORT = 12900;
       // final String SERVER_NAME = "192.168.43.136"; 
       byte ipAddr[];
       ipAddr = new byte[4];
      ipAddr[0]=(byte) 192;
	     ipAddr[1]=(byte) 168;
	   ipAddr[2] = (byte) 43;
	ipAddr[3] = (byte) 136;
       DatagramSocket datagramSocket = new DatagramSocket(LOCAL_PORT, InetAddress.getByAddress(ipAddr));
    System.out.println("Created UDP server socket at " + datagramSocket.getLocalSocketAddress() + "...");
    

    //Wait for a message in a loop and echo the same message to the sender
    while(true)
    {
        System.out.println("Waiting for a UDP packet" + " to arrive...");
        
        //Prepare a packet to hold the received data
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        
        //Receive a packet
        datagramSocket.receive(packet);
        
        //Print the packet details
        displayPacketDetails(packet);
        
        //Echo the same packet to the sender
        datagramSocket.send(packet);
    }
    }catch(IOException t){t.printStackTrace();}
    
    }   
        public static void displayPacketDetails(DatagramPacket packet)
        {
            byte[] msgBuffer = packet.getData();
            int length = packet.getLength();
            int offset = packet.getOffset();
            
            int remotePort = packet.getPort();
            InetAddress remoteAddr = packet.getAddress();
            String msg = new String(msgBuffer, offset, length);
            System.out.println("Received a packet:[[IP Address=" + remoteAddr +
                     ", port=" + remotePort + ", message=" + msg + "]");
        }

} 

