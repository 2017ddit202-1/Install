package com.ddit.getinfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandUsageException;

public class InstallThread extends Thread {
	
	static String ip; // 아이피
	static String hostname;// 호스트 네임
	static String os_version;// win32
	static String os_name; // windows7
	static String os_support; // 32,64

	static String networkcard; // 네트워크 카드

	static String cpu_user; // cpu 유저 사용량
	static String cpu_sys; // cpu 시스템 사용량
	static String cpu_idle; // cpu 휴먼 사용량
	static String cpu_total; // cpu 총 사용량

	static String memory_max; // 최대 메모리
	static String memory_used; // 현재 메모리 사용량
	static String memory_idle; // 휴먼 메모리
	static String memory_total; // 총 메모리 사용량(%)

	static String diskAll; // 디스크 사용량

	static String networkrx; // 네트워크 수신
	static String networktx; // 네트워크 송신
	
	static String[] arg;
	static TrafficInfo traffic;
	
	public InstallThread(String[] arg, TrafficInfo traffic,String networkcard,String ip,String hostname,String os_version,String os_name,String os_support){
		this.arg = arg;
		this.traffic = traffic;
		this.networkcard = networkcard;
		this.ip = ip;
		this.hostname = hostname;
		this.os_version = os_version;
		this.os_name = os_name;
		this.os_support = os_support;
	}
	
	 public void run() {
		 while(true){
			 
	         CpuInfo cpu = new CpuInfo();
	         DiskInfo disk = new DiskInfo();
	         MemoryInfo memory = new MemoryInfo();
	         Long[] m = null;
	               
	         try {
	            cpu.processCommand(arg);
	            disk.processCommand(arg);
	            memory.processCommand(arg);
	            m = traffic.getMetric();

	         } catch (ShellCommandUsageException e) {

	         } catch (ShellCommandExecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	         long totalrx = m[0];
	         long totaltx = m[1];
	         String rx = Sigar.formatSize(totalrx);
	         String tx = Sigar.formatSize(totaltx);
	         networkrx = rx;
	         networktx = tx;
	           
	         diskAll = disk.disk;

	         cpu_user = cpu.user;
	         cpu_sys = cpu.Sys;
	         cpu_idle = cpu.Idle;
	         cpu_total = cpu.total;

	         memory_max = memory.Total;
	         memory_used = memory.Used;
	         memory_idle = memory.idle;
	         memory_total = memory.RAM;


	         URI url;
	         try {
	            /*
	             * ip = InetAddress.getLocalHost().getHostAddress(); hostName =
	             * InetAddress.getLocalHost().getHostName();
	             * System.out.println(ip);
	             */
	            url = new URI(
	                  "http://192.168.202.143:8181/observer/server/serverMain?testIp="
	                        + ip);
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

	            String saveyn = "0";
	            nameValuePairs.add(new BasicNameValuePair("ip", ip));
	            nameValuePairs
	                  .add(new BasicNameValuePair("hostName", hostname));
	            nameValuePairs.add(new BasicNameValuePair("saveyn", saveyn));
	            nameValuePairs.add(new BasicNameValuePair("os_version", os_version));
	            nameValuePairs.add(new BasicNameValuePair("os_name", os_name));
	            nameValuePairs.add(new BasicNameValuePair("os_support", os_support));
	            
	            nameValuePairs.add(new BasicNameValuePair("cpu_pcnt", cpu_sys));
	            nameValuePairs.add(new BasicNameValuePair("cpu_user_pcnt",   cpu_user));
	            nameValuePairs.add(new BasicNameValuePair("cpu_total_pcnt",   cpu_total));
	            nameValuePairs.add(new BasicNameValuePair("cpu_idle", cpu_idle));
	            nameValuePairs.add(new BasicNameValuePair("cpu_ip", ip));
	            
	            nameValuePairs.add(new BasicNameValuePair("memory_total", memory_max));
	            nameValuePairs.add(new BasicNameValuePair("memory_using", memory_used));
	            nameValuePairs.add(new BasicNameValuePair("memory_idle", memory_idle));
	            nameValuePairs.add(new BasicNameValuePair("memory_total_used", memory_idle));
	            
	            nameValuePairs.add(new BasicNameValuePair("networkcard", networkcard));
	            
	            nameValuePairs.add(new BasicNameValuePair("diskAll", diskAll));
	            
	            nameValuePairs.add(new BasicNameValuePair("networkrx", networkrx));
	            nameValuePairs.add(new BasicNameValuePair("networktx", networktx));
	            
	            
	            
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
	                  "utf-8"));

	            HttpResponse response = httpclient.execute(httpPost);
	            HttpEntity respEntity = response.getEntity();

	            if (respEntity != null) {
	               String content = EntityUtils.toString(respEntity);
	               System.out.println(content);
	            }
	            
	            System.out.println(ip);
	            System.out.println(hostname);
	            System.out.println(os_name);
	            System.out.println(os_support);
	            System.out.println(os_version);
	            System.out.println(networkcard);
	            System.out.println(cpu_idle);
	            System.out.println(cpu_sys);
	            System.out.println(cpu_total);
	            System.out.println(cpu_user);
	            System.out.println(memory_idle);
	            System.out.println(memory_max);
	            System.out.println(memory_total);
	            System.out.println(memory_used);
	            System.out.println(diskAll);
	            System.out.println(networkrx);
	            System.out.println(networktx);

	     	            
	            
	            Thread.sleep(10000);
	         } catch (URISyntaxException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        
			 
		 }
		 
	    }
	 
}
