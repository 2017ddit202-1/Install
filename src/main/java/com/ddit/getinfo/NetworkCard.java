package com.ddit.getinfo;

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkCard {
	
	 public static void main(String args[]) throws SocketException {
		 
	        NetworkInterface nets = NetworkInterface.getByName("eth3");
	       /* System.out.println(nets); */
	    }
}
