package com.machine;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DirectByteBufferTest {

    @Test
    public void direct() throws InterruptedException {
        //分配128MB直接内存
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 128);

        TimeUnit.SECONDS.sleep(100);
        System.out.println("ok");
    }

    static final String CONNECTOR_ADDRESS =
            "com.sun.management.jmxremote.localConnectorAddress";

    @Test
    public void buffer() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, MalformedObjectNameException, InterruptedException {
        // attach to target VM to get connector address
        VirtualMachine vm = VirtualMachine.attach("4176");
        String connectorAddress = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
        if (connectorAddress == null) {
            // start management agent
            String agent = vm.getSystemProperties().getProperty("java.home") +
                    File.separator + "lib" + File.separator + "management-agent.jar";
            vm.loadAgent(agent);
            connectorAddress = vm.getAgentProperties().getProperty(CONNECTOR_ADDRESS);
            assert connectorAddress != null;
        }

        // connect to agent
        JMXServiceURL url = new JMXServiceURL(connectorAddress);
        JMXConnector c = JMXConnectorFactory.connect(url);
        MBeanServerConnection server = c.getMBeanServerConnection();

        // get the list of pools
        Set<ObjectName> mbeans = server.queryNames(
                new ObjectName("java.nio:type=BufferPool,*"), null);
        List<BufferPoolMXBean> pools = new ArrayList<BufferPoolMXBean>();
        for (ObjectName name : mbeans) {
            BufferPoolMXBean pool = ManagementFactory
                    .newPlatformMXBeanProxy(server, name.toString(), BufferPoolMXBean.class);
            pools.add(pool);
        }

        // print headers
        for (BufferPoolMXBean pool : pools)
            System.out.format("         %8s             ", pool.getName());
        System.out.println();
        for (int i = 0; i < pools.size(); i++)
            System.out.format("%6s %10s %10s  ", "Count", "Capacity", "Memory");
        System.out.println();

        // poll and print usage
        for (; ; ) {
            for (BufferPoolMXBean pool : pools) {
                System.out.format("%6d %10d %10d  ",
                        pool.getCount(), pool.getTotalCapacity(), pool.getMemoryUsed());
            }
            System.out.println();
            Thread.sleep(2000);
        }
    }
}
