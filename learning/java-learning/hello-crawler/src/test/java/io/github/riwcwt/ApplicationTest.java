package io.github.riwcwt;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Unit test for simple Application.
 */
public class ApplicationTest {
    @org.junit.Test
    public void file() throws IOException {
        FileUtils.copyURLToFile(new URL("https://download.docker.com/linux/centos/7/x86_64/stable/Packages/docker-ce-17.09.0.ce-1.el7.centos.x86_64.rpm"), new File("docker.rpm"));
    }
}
