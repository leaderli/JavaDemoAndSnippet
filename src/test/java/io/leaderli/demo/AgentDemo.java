package io.leaderli.demo;


import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * @author leaderli
 * @since 2022/11/7 12:56 PM
 */
public class AgentDemo {

    public static void main(String[] args) throws IOException, AttachNotSupportedException {

        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(jvmName);
        String jvmPid = jvmName.substring(0, jvmName.indexOf('@'));

        System.out.println(VirtualMachine.class.getProtectionDomain().getCodeSource().getLocation());
        VirtualMachine self = VirtualMachine.attach(jvmPid);
        System.out.println(self);

    }
}
