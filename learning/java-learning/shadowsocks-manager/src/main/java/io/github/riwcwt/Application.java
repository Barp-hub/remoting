package io.github.riwcwt;

import java.util.Scanner;

/**
 * Hello world!
 */
public class Application {

    public static final String CONFIG = "E:/repository/github/remoting/learning/java-learning/shadowsocks-manager/src/main/resources/shadowsocks.json";

    public static void main(String[] args) {

        String help = "****************************\n" +
                "1. help        输出帮助信息\n" +
                "2. list        显示打印出当前所有用户\n" +
                "3. add user    添加用户\n" +
                "4. delete port 根据断开删除用户\n" +
                "5. reboot      修改后，重启系统生效\n" +
                "6. quit        退出\n" +
                "****************************";
        System.out.println(help);
        String command;
        ShadowsocksOperator operator = new ShadowsocksOperator();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("->");
            command = scanner.nextLine();
            while (!"quit".equalsIgnoreCase(command)) {
                if (command != null) {
                    if ("help".equalsIgnoreCase(command)) {
                        System.out.println(help);
                    } else if (command.toLowerCase().startsWith("add")) {
                        String[] arguments = command.split(" ");
                        if (arguments.length == 2) {
                            operator.add(arguments[1]);
                        }
                    } else if (command.toLowerCase().startsWith("delete")) {
                        String[] arguments = command.split(" ");
                        if (arguments.length == 2) {
                            operator.delete(arguments[1]);
                        }
                    } else if ("list".equalsIgnoreCase(command)) {
                        operator.list();
                    } else if ("reboot".equalsIgnoreCase(command)) {
                        operator.reboot();
                    } else {
                        System.out.println("不支持命令：" + command);
                    }
                }
                System.out.print("->");
                command = scanner.nextLine();
            }
        }
        System.out.println("成功退出！");
    }
}
