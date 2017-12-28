package io.github.riwcwt.util;

public class JvmUtil {

    public static boolean isJDK() {
        try {
            Class.forName("com.sun.tools.attach.VirtualMachine");
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

}
