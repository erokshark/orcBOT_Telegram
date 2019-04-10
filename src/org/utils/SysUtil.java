package org.utils;

import java.io.IOException;

/**
 *
 * @author erokshark
 */
public class SysUtil {

    public String reverseString(String param) {
        return new StringBuilder(param).reverse().toString();
    }    

    public static Integer getMaxRamWindows() throws IOException {
        String result = SysUtil.execCmdWindows("wmic OS Get TotalVisibleMemorySize /Value");        
        return Integer.valueOf(result.substring(result.indexOf("=") + 1, result.length()).trim()) / 1024;  
    }
    
    public static Integer getFreeRamWindows() throws IOException {
        String result = SysUtil.execCmdWindows("wmic OS get FreePhysicalMemory /Value");
        return Integer.valueOf(result.substring(result.indexOf("=") + 1, result.length()).trim()) / 1024;        
    }
    
    public static String execCmdWindows(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(String.format("cmd /c %s", cmd)).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
    public static String getMemoryUsageWindows() throws java.io.IOException {
        return String.format("Memory Usage: %s/%sMB", getMaxRamWindows() - getFreeRamWindows(), getMaxRamWindows());
    }

    public static String getMemoryUsageUnix() {
        return "Not supported yet."; //implement memory usage linux/ubuntu
    }
    
}
