package com.ddit.getinfo;

import java.net.NetworkInterface;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandUsageException;
import org.json.simple.JSONObject;

public class GetinfoSend {
	
	
	public static void main(String[] args) throws Exception { 
		NetworkInterface nets = NetworkInterface.getByName("eth3");
		/*System.out.println(nets);*/
		
		Thread1 t1 = new Thread1();
		t1.setDaemon(false);
		t1.start();
		newCpuThread(args);
	
		new CpuInfo().processCommand(args);
		
		new DiskInfo().processCommand(args);
		new MemoryInfo().processCommand(args);
		new VersionInfo().processCommand(args);
		new TrafficInfo(new Sigar());
        TrafficInfo.newMetricThread();
        
	}
	
	  public static void newCpuThread(String[] args) throws SigarException, InterruptedException {
	        while (true) {
	        	try {
					new CpuInfo().processCommand(args);
					new DiskInfo().processCommand(args);
					new MemoryInfo().processCommand(args);
					
				} catch (ShellCommandUsageException | ShellCommandExecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Thread.sleep(1000);
	            
	        }

	    }
	
}
class Thread1 extends Thread{
	  //  run() 메서드 오버라이딩
		public void run(){
			try {
				new TrafficInfo(new Sigar());
				TrafficInfo.newMetricThread();
			} catch (SigarException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

		}
	}
