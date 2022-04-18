//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paylibliqpay;

public class LogUtil {
    public static boolean isDebug = true;

    public LogUtil() {
    }

    public static void log(String s) {
        if (isDebug) {
            System.out.println("lpLog >> " + s);
        }

    }
}
