package com.ddit.getinfo;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.NetworkInterface;

import org.hyperic.sigar.Sigar;

public class GetinfoSend {

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
	static SystemTray tray = SystemTray.getSystemTray();
	static TrayIcon trayIcon;
	
	static InstallThread install;
	static InstallWatchThread installwatch;

	public static void main(String[] args) throws Exception {
		arg = args;
		NetworkInterface nets = NetworkInterface.getByName("eth3");
		networkcard = nets + "";
		networkcard = networkcard.substring(11);
		networkcard = networkcard.replaceAll("\\)", " ");

		VersionInfo version = new VersionInfo();
		version.processCommand(args);
		ip = version.fqdn;
		hostname = version.host;
		os_version = version.OS_name;
		os_name = version.OS_description;
		os_support = version.OS_arch;
		
		traffic = new TrafficInfo(new Sigar());
		
		if (SystemTray.isSupported()) {

			Image image = Toolkit.getDefaultToolkit().getImage(
					"C:/Program Files (x86)/install/icon.png");
			PopupMenu popup = new PopupMenu();
			trayIcon = new TrayIcon(image, "Install", popup);
			trayIcon.setImageAutoSize(true);
			
			
			
			MenuItem item = new MenuItem("start");
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					install = new InstallThread(arg, traffic,
							networkcard, ip, hostname, os_version, os_name,
							os_support);
					install.setDaemon(false);
					installwatch = new InstallWatchThread(
							arg, traffic, networkcard, ip, hostname,
							os_version, os_name, os_support);
					installwatch.setDaemon(false);
					install.start();
					installwatch.start();
				}
			});
			popup.add(item);
			item = new MenuItem("stop");
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					install.Stop();
					installwatch.Stop();
				}
			});
			

			popup.add(item);
			item = new MenuItem("close");
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					tray.remove(trayIcon);
					System.exit(0);
				}
			});
			popup.add(item);

			

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("Can not add to tray");
			}
		} else {
			System.err.println("Tray unavailable");
		}

	}

}
