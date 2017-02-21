package com.ddit.getinfo;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;
 
/**
 * Display cpu information for each cpu found on the system. 
 */ 
public class CpuInfo extends SigarCommandBase { 
	
	String user;
	String Sys;
	String Idle;
	String total;
	
    public boolean displayTimes = true; 
     
    public CpuInfo(Shell shell) { 
        super(shell); 
    } 
 
    public CpuInfo() { 
        super(); 
    } 
 
    public String getUsageShort() { 
        return "Display cpu information"; 
    } 
 
    private void output(CpuPerc cpu) { 
    	user = CpuPerc.format(cpu.getUser());
    	Sys = CpuPerc.format(cpu.getSys());
    	Idle = CpuPerc.format(cpu.getIdle());
    	
    	String[] users = user.split("%");
    	String[] Syss = Sys.split("%");
    	String[] IdelS = Idle.split("%");
    	
    	
    	user = users[0];
    	Sys = Syss[0];
    	Idle = IdelS[0];
    	
    	float convert =  Float.parseFloat(user) + Float.parseFloat(Sys);
    	
    	total = Math.round(convert*100)/100F +"";
    	
    	//System.out.println(total);
    	
    	/*System.out.println(user);
    	System.out.println(Sys);
    	System.out.println(Idle);
    	
        println(""); */
        
       
    } 
    
    
    
    public void output(String[] args) throws SigarException { 
 
        if (!this.displayTimes) { 
            return; 
        } 
        
        output(this.sigar.getCpuPerc()); 
    } 
 
    public static void main(String[] args) throws Exception { 
        new CpuInfo().processCommand(args); 
    } 
}

