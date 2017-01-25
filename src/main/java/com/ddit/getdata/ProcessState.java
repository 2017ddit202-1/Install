package com.ddit.getdata;

import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class ProcessState { 
	 
    private static String getStateString(char state) { 
        switch (state) { 
          case ProcState.SLEEP: 
            return "Sleeping"; 
          case ProcState.RUN: 
            return "Running"; 
          case ProcState.STOP: 
            return "Suspended"; 
          case ProcState.ZOMBIE: 
            return "Zombie"; 
          case ProcState.IDLE: 
            return "Idle"; 
          default: 
            return String.valueOf(state); 
        } 
    } 
 
    public static void main(String[] args) 
        throws SigarException { 
 
        String pid; 
        if (args.length == 0) { 
            pid = "$$"; //default to this process 
        } 
        else { 
            pid = args[0]; 
        } 
 
        Sigar sigar = new Sigar(); 
 
        ProcState procState = sigar.getProcState(pid); 
        String state; 
 
        System.out.println(procState.getName() + ": " + 
                           getStateString(procState.getState())); 
 
        sigar.close(); 
    } 
}
