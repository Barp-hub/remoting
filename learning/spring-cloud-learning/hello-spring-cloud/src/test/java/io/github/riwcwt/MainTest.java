package io.github.riwcwt;

import com.netflix.discovery.util.SystemUtil;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by michael on 2017-03-23.
 */
public class MainTest {

    @Test
    public void ip() throws UnknownHostException {
        System.out.println("Found IP=" + SystemUtil.getServerIPv4());
        System.out.println("ip : " + Inet4Address.getLocalHost().getHostName());
    }

}
