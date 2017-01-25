package com.ddit.getdata;

import java.util.HashMap;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

public class GetServerData {
	public static void main(String[] args) throws SigarException {
        Sigar sigar = new Sigar(); //1. sigar객체 생성

        CpuPerc cpu = sigar.getCpuPerc(); //2. 전체 cpu에 대한 사용량
        CpuPerc[] cpus = sigar.getCpuPercList(); //3. 각 cpu에 대한 사용량
        
        //4. cpu사용량 출력
        System.out.println("Total cpu----");
        cpu_output(cpu);
      
        for(int i=0; i < cpus.length; i++) {
             System.out.println("cpu"+i+"----");
             cpu_output(cpus[i]);
        }
        
        Mem mem = sigar.getMem();
        Swap swap = sigar.getSwap();

        HashMap<Integer, String[]> hm = new HashMap<Integer, String[]>();
      
        hm.put(0, new String[]{"total", "used", "free"} );
        hm.put(1, new String[]{format(mem.getTotal()), format(mem.getUsed()), format(mem.getFree())});
        hm.put(2, new String[]{"", format(mem.getActualUsed()), format(mem.getActualFree())});
        hm.put(3, new String[]{format(swap.getTotal()), format(swap.getUsed()), format(swap.getFree())});
      
        for(int i=0; i < hm.size(); i++) {
             mem_output(hm.get(i));
        }
        
      
        
   }
 
   public static void cpu_output(CpuPerc cpu) {
        System.out.println("User Time\t :"+CpuPerc.format(cpu.getUser()));
        System.out.println("Sys Time\t :"+CpuPerc.format(cpu.getSys()));
        System.out.println("Idle Time\t :"+CpuPerc.format(cpu.getSys()));        
   }
   
   public static String format(Long value) {
       return Long.toString(new Long(value / 1024));
  }

  public static void mem_output(String[] value) {
       String str = "";
       for(int i=0; i < value.length; i++) {
            str += value[i] + "\t";
       }
       System.out.println(str);
  }
   
   
}
