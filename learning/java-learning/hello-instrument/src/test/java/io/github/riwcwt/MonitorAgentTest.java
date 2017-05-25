package io.github.riwcwt;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Unit test for simple MonitorAgent.
 */
public class MonitorAgentTest {

    @Test
    public void consume() {
    }

    @Test
    public void jvm() {
        List<VirtualMachineDescriptor> jvms = VirtualMachine.list();
        for (VirtualMachineDescriptor jvm : jvms) {
            System.out.println("Running JVM: " + jvm.id() + " - " + jvm.displayName());
        }
    }

    @Test
    public void attach() {
        String agentFilePath = "E:\\repository\\github\\remoting\\learning\\java-learning\\hello-instrument\\target\\hello-instrument.jar";
        String jvmAppName = "target\\spring-boot.jar";
        String jvmPid = null;
        List<VirtualMachineDescriptor> jvms = VirtualMachine.list();
        for (VirtualMachineDescriptor jvm : jvms) {
            System.out.println("Running JVM: " + jvm.id() + " - " + jvm.displayName());
            if (jvm.displayName().equals(jvmAppName)) {
                jvmPid = jvm.id();
            }
        }
        if (jvmPid != null) {
            File agentFile = new File(agentFilePath);
            if (agentFile.isFile()) {
                String agentFileName = agentFile.getName();
                String agentFileExtension = agentFileName.substring(agentFileName.lastIndexOf(".") + 1);
                if (agentFileExtension.equalsIgnoreCase("jar")) {
                    try {
                        System.out.println("Attaching to target JVM with PID: " + jvmPid);
                        VirtualMachine jvm = VirtualMachine.attach(jvmPid);
                        jvm.loadAgent(agentFile.getAbsolutePath());
                        jvm.detach();
                        System.out.println("Attached to target JVM and loaded Java agent successfully");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            System.out.println("Target JVM running demo Java application not found");
        }

    }

}
