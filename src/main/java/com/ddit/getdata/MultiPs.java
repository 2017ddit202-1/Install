package com.ddit.getdata;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.MultiProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

 
/**
 * Show multi process status. 
 */ 
public class MultiPs extends SigarCommandBase { 
 
    public MultiPs(Shell shell) { 
        super(); 
    } 
 
    public MultiPs() { 
        super(); 
    } 
 
    protected boolean validateArgs(String[] args) { 
        return args.length == 1; 
    } 
 
    public String getSyntaxArgs() { 
        return "query"; 
    } 
 
    public String getUsageShort() { 
        return "Show multi process status"; 
    } 
 
    public boolean isPidCompleter() { 
        return true; 
    } 
 
    public void output(String[] args) throws SigarException { 
        String query = args[0]; 
        MultiProcCpu cpu = this.proxy.getMultiProcCpu(query); 
        println("Number of processes: " + cpu.getProcesses()); 
        println("Cpu usage: " + CpuPerc.format(cpu.getPercent())); 
        println("Cpu time: "  + Ps.getCpuTime(cpu.getTotal())); 
 
        ProcMem mem = this.proxy.getMultiProcMem(query); 
        println("Size: " + Sigar.formatSize(mem.getSize())); 
        println("Resident: " + Sigar.formatSize(mem.getResident())); 
        println("Share: " + Sigar.formatSize(mem.getShare())); 
    } 
 
    public static void main(String[] args) throws Exception { 
        new MultiPs().processCommand(args); 
    } 
} 
