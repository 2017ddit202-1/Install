package com.ddit.getdata;

import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;

/**
 * Send a signal to a process. 
 */ 
public class Kill extends SigarCommandBase { 
 
    public Kill(Shell shell) { 
        super(shell); 
    } 
 
    public Kill() { 
        super(); 
    } 
 
    protected boolean validateArgs(String[] args) { 
        return args.length == 1 || args.length == 2; 
    } 
 
    public String getSyntaxArgs() { 
        return "[signal] <query|pid>"; 
    } 
 
    public String getUsageShort() { 
        return "Send signal to a process"; 
    } 
 
    public boolean isPidCompleter() { 
        return true; 
    } 
 
    public void output(String[] args) throws SigarException { 
        String signal = "SIGTERM"; 
        long[] pids; 
        String query; 
 
        if (args.length == 2) { 
            signal = args[0]; 
            query = args[1]; 
        } 
        else { 
            query = args[0]; 
        } 
 
        pids = this.shell.findPids(new String[] { query }); 
 
        for (int i=0; i<pids.length; i++) { 
            println("kill " + signal + " " + pids[i]); 
            this.sigar.kill(pids[i], signal); 
        } 
    } 
 
    public static void main(String[] args) throws Exception { 
        new Kill().processCommand(args); 
    } 
}
