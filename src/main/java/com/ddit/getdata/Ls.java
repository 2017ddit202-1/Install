package com.ddit.getdata;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hyperic.sigar.FileInfo;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.SigarCommandBase;
 
public class Ls extends SigarCommandBase { 
 
    public Ls(Shell shell) { 
        super(); 
    } 
 
    public Ls() { 
        super(); 
    } 
 
    public String getUsageShort() { 
        return "simple FileInfo test at the moment (like ls -l)"; 
    } 
 
    protected boolean validateArgs(String[] args) { 
        return args.length == 1; 
    } 
 
    private String getDate(long mtime) { 
        final String fmt = "MMM dd  yyyy"; 
 
        return new SimpleDateFormat(fmt).format(new Date(mtime)); 
    } 
 
    public void output(String[] args) throws SigarException { 
        String file = args[0]; 
        FileInfo link = this.sigar.getLinkInfo(file); 
        FileInfo info = this.sigar.getFileInfo(file); 
        if (link.getType() == FileInfo.TYPE_LNK) { 
            try { 
                file = file + " -> " + new File(file).getCanonicalPath(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
 
        println(link.getTypeChar() +  
                info.getPermissionsString() + "\t" + 
                info.getUid() + "\t" + info.getGid() + "\t" + 
                info.getSize() + "\t" + 
                getDate(info.getMtime()) + "\t" + 
                file); 
    } 
 
    public static void main(String[] args) throws Exception { 
        new Ls().processCommand(args); 
    } 
}
