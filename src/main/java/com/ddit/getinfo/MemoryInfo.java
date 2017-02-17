package com.ddit.getinfo;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;


public class MemoryInfo extends SigarCommandBase {
	
    public MemoryInfo(Shell shell) { 
        super(); 
    } 
 
    public MemoryInfo() { 
        super(); 
    } 
 
    public String getUsageShort() { 
        return "Display information about free and used memory"; 
    } 
 
    private static Long format(long value) { 
        return new Long(value / 1024); 
    } 
 
    public void output(String[] args) throws SigarException { 
        Mem mem   = this.sigar.getMem(); 
        Swap swap = this.sigar.getSwap(); 
        
        double tRam = mem.getTotal()/1000000000d;
        float totalRam = Math.round(tRam*100)/100F;   
        
        double uRam = mem.getUsed()/1000000000d;
        float usedRam = Math.round(uRam*100)/100F;   
               
        float u100 = mem.getUsed() * 100; 
        float pct = u100 / mem.getTotal() + 
            ((u100 % mem.getTotal() != 0) ? 1 : 0);
        
        float memusedpct = Math.round(pct*100)/100F;
        
       /* System.out.println("RAM "+memusedpct + "%");
        System.out.println("Total "+totalRam + "Gbyte");
        System.out.println("Used "+usedRam + "Gbyte");*/
    } 
 
    public static void main(String[] args) throws Exception { 
        new MemoryInfo().processCommand(args); 
    } 
}
