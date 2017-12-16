package com.jenkov.nioserver.http;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaptureScreenTest {

    @Test
    public void capture() throws AWTException, IOException {
        Robot robot = new Robot();
        String format = "jpg";
        String file = "screen." + format;

        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(rectangle);

        ImageIO.write(image, format, new File(file));
    }

}
