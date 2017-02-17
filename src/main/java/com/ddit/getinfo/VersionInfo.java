package com.ddit.getinfo;

import java.io.File;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;

public class VersionInfo extends SigarCommandBase {
	 static String OS_description;
	 static String OS_name;
	 static String OS_arch;
     
	 static String OS_version;
	 static String OS_patch_level;
	 static String OS_vandor;
	 static String OS_vendor_version;
	 
	 static String host;
     static String fqdn;
	public VersionInfo(Shell shell) { 
        super(shell); 
    } 
 
    public VersionInfo() { 
        super(); 
    } 
 
    public String getUsageShort() { 
        return "Display sigar and system version info"; 
    } 
 
    private static String getHostName() { 
        try { 
            return InetAddress.getLocalHost().getHostName(); 
        } catch (UnknownHostException e) { 
            return "unknown"; 
        } 
    } 
 
    private static void printNativeInfo(PrintStream os) { 
 
        String archlib = 
            SigarLoader.getNativeLibraryName(); 
 
        host = getHostName(); 
        Sigar sigar = new Sigar();  
        try { 
            File lib = sigar.getNativeLibrary(); 
            if (lib != null) { 
                archlib = lib.getName(); 
            } 
            fqdn = sigar.getFQDN(); 
        } catch (SigarException e) { 
            fqdn = "unknown"; 
        } finally { 
            sigar.close(); 
        } 
        
        os.println("Current fqdn........" + fqdn); 
        if (!fqdn.equals(host)) { 
            os.println("Hostname............" + host); 
        }         
 
    } 
     
    public static void printInfo(PrintStream os) { 
        try { 
            printNativeInfo(os); 
        } catch (UnsatisfiedLinkError e) { 
            os.println("*******ERROR******* " + e); 
        } 
 
        os.println("Current user........" + 
                   System.getProperty("user.name")); 
        os.println(""); 
         
        OperatingSystem sys = OperatingSystem.getInstance();
        OS_description = sys.getDescription();
        OS_name = sys.getName();
        OS_arch = sys.getArch();
        
        OS_version = sys.getVersion();
        OS_patch_level = sys.getPatchLevel();
        OS_vandor = sys.getVendor();
        OS_vendor_version = sys.getVendorVersion();
        
        os.println("OS description......" + sys.getDescription()); 
        os.println("OS name............." + sys.getName()); 
        os.println("OS arch............." + sys.getArch()); 

        os.println("OS version.........." + sys.getVersion()); 
        os.println("OS patch level......" + sys.getPatchLevel()); 
        os.println("OS vendor..........." + sys.getVendor()); 
        os.println("OS vendor version..." + sys.getVendorVersion()); 
      
    } 
 
    public void output(String[] args) { 
        printInfo(this.out);
    } 
 
    public static void main(String[] args) throws Exception { 
        new VersionInfo().processCommand(args); 
    } 
}
