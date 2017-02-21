package com.ddit.getinfo;

import java.net.NetworkInterface;
import java.net.SocketException;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandUsageException;

public class GetinfoSend {
	
	static String ip; //아이피
	static String hostname;//호스트 네임
	static String os_version;//win32
	static String os_name; //windows7
	static String os_support; //32,64
	
	static String networkcard; // 네트워크 카드
	
	static String cpu_user; //cpu 유저 사용량
	static String cpu_sys; // cpu 시스템 사용량
	static String cpu_idle; // cpu 휴먼 사용량
	static String cpu_total; // cpu 총 사용량
	
	static String memory_max; // 최대 메모리
	static String memory_used; // 현재 메모리 사용량
	static String memory_idle; // 휴먼 메모리
	static String memory_total; // 총 메모리 사용량(%)
	
	static String diskAll; //디스크 사용량
	
	static String networkrx; //네트워크 수신
	static String networktx; //네트워크 송신
	
	public static void main(String[] args) throws Exception { 
		NetworkInterface nets = NetworkInterface.getByName("eth3");
		networkcard = nets+"";
		networkcard = networkcard.substring(11);
		networkcard = networkcard.replaceAll("\\)"," ");
		
		/*System.out.println(networkcard);*/
		VersionInfo version = new VersionInfo();
		version.processCommand(args);
		ip = version.fqdn;
		hostname = version.host;
		os_version = version.OS_name;
		os_name = version.OS_description;
		os_support = version.OS_arch;
		
		/*System.out.println(nets);*/
		/*TrafficThread traffic = new TrafficThread();
		traffic.setDaemon(false);
		traffic.start();*/
		
		TrafficInfo traffic = new TrafficInfo(new Sigar());		
	
		newCpuThread(args,traffic);
		/*new CpuInfo().processCommand(args);	
		new DiskInfo().processCommand(args);
		new MemoryInfo().processCommand(args);
		new VersionInfo().processCommand(args);
		new TrafficInfo(new Sigar());
        TrafficInfo.newMetricThread();*/
        
	}
	
	  public static void newCpuThread(String[] args,TrafficInfo traffic) throws SigarException, InterruptedException, SocketException, ShellCommandExecException {
	        while (true) {
	        	NetworkInterface nets = NetworkInterface.getByName("eth3");
	        	CpuInfo cpu = new CpuInfo();
	        	DiskInfo disk = new DiskInfo();
	        	MemoryInfo memory = new MemoryInfo();	        	
	               
	    		try {
					cpu.processCommand(args);
					disk.processCommand(args);
					memory.processCommand(args);
					
				} catch (ShellCommandUsageException e) {
					
				}
	    		Long[] m = traffic.getMetric();
				long totalrx = m[0];
				long totaltx = m[1];
				String rx = Sigar.formatSize(totalrx);
				String tx = Sigar.formatSize(totaltx);
				networkrx = rx;
				networktx = tx;
	
				diskAll = disk.disk;
				/*System.out.println(disk.disk);*/
				
				cpu_user=cpu.user; 
				cpu_sys=cpu.Sys; 
				cpu_idle=cpu.Idle; 
				cpu_total=cpu.total; 
				
				/*System.out.println(cpu_user);
				System.out.println(cpu_sys);
				System.out.println(cpu_total);*/
				
				memory_max = memory.Total; 
				memory_used = memory.Used;
				memory_idle = memory.idle;
				memory_total = memory.RAM; 
				
				
			/*	System.out.println(ip);
				System.out.println(hostname);
				System.out.println(os_version);
				System.out.println(os_name);
				System.out.println(os_support);
	    		
				System.out.println(networkcard);*/
				
				
				/*System.out.println("Sys : "+cpu.Sys);
				System.out.println("user :"+cpu.user);
				System.out.println("idle :"+cpu.Idle);
				
				System.out.print("totalrx(download): ");
				System.out.println("\t" + Sigar.formatSize(totalrx));
				System.out.print("totaltx(upload): ");
				System.out.println("\t" + Sigar.formatSize(totaltx));
				System.out.println("-----------------------------------");*/
				
/*				System.out.println(memory.RAM);
				System.out.println(memory.Total);
				System.out.println(memory.Used);
				System.out.println(memory.idle);*/
	            Thread.sleep(1000);
	            
	        }

	    }
	
}
/*class TrafficThread extends Thread{
		public void run(){
			try {
				new TrafficInfo(new Sigar());
				TrafficInfo.newMetricThread();
			} catch (SigarException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/