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
        println("User Time....." + CpuPerc.format(cpu.getUser())); 
        println("Sys Time......" + CpuPerc.format(cpu.getSys())); 
        println("Idle Time....." + CpuPerc.format(cpu.getIdle())); 
        if (SigarLoader.IS_LINUX) { 
            println("SoftIrq Time.." + CpuPerc.format(cpu.getSoftIrq())); 
            println("Stolen Time...." + CpuPerc.format(cpu.getStolen())); 
        } 
        println(""); 
    } 
 
    public void output(String[] args) throws SigarException { 
 
        if (!this.displayTimes) { 
            return; 
        } 
        
        println("Totals........"); 
        output(this.sigar.getCpuPerc()); 
    } 
 
    public static void main(String[] args) throws Exception { 
        new CpuInfo().processCommand(args); 
    } 
}

