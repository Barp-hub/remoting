package io.github.riwcwt;

import io.github.riwcwt.util.JvmUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws IOException {
        System.out.println("is jdk : " + JvmUtil.isJDK());
        FileUtils.copyURLToFile(new URL("https://stlib.qbb6.com/cnt0/chapter/0/2110rnCoNB/audioCourse6751777535067056001.mp3"), new File("./001.mp3"));
    }
}
