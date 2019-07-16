package com.acme.edu;

import static java.lang.System.lineSeparator;

public class Logger {
    private static int accumulatedSum = 0;
    private static String accumulatedStr = "";
    private static String previousStr = "";
    private static boolean isEmptyAccumInt = true;
    private static boolean isEmptyAccumByte = true;
    private static boolean isPrimitive = true;
    private static int sameStringsAmount = 0;
    private static String prevTypeName = "";

    private static void typeSwitcher(String typeName) {
        if (prevTypeName.equals(typeName)) return;
        switch (prevTypeName) {
            case "int":
            case "byte": {
                accumulatedStr += accumulatedSum + lineSeparator();
                accumulatedSum = 0;
                break;
            }
            case "string": {
                accumulatedStr += previousStr;
                if (sameStringsAmount > 1) {
                    accumulatedStr += " (x" + sameStringsAmount + ")";
                }
                accumulatedStr += lineSeparator();
                previousStr = "";
                sameStringsAmount = 0;
            }
            default:
        }
    }

    public static void log(int message) {
        typeSwitcher("int");
        prevTypeName = "int";
        if (accumulatedSum > Integer.MAX_VALUE - message) {
            accumulatedStr += accumulatedSum + lineSeparator();
            accumulatedSum = Integer.MAX_VALUE;
        } else {
            accumulatedSum += message;
        }
    }

    public static void log(byte message) {
        typeSwitcher("byte");
        prevTypeName = "byte";
        if (accumulatedSum > Byte.MAX_VALUE - message) {
            accumulatedStr += accumulatedSum + lineSeparator();
            accumulatedSum = Byte.MAX_VALUE;
        } else {
            accumulatedSum += message;
        }
    }

    public static void log(char message) {
        System.out.println("char: " + message);
    }

    public static void log(String message) {
        typeSwitcher("string");
        prevTypeName = "string";
        if (previousStr.equals(message)) {
            sameStringsAmount++;
        } else {
            if (!previousStr.equals("")) {
                accumulatedStr += previousStr;
                if (sameStringsAmount > 1) {
                    accumulatedStr += " (x" + sameStringsAmount + ")";
                }
                accumulatedStr += lineSeparator();
            }
            previousStr = message;
            sameStringsAmount = 1;
        }
    }

    public static void log(boolean message) {
        System.out.println("primitive: " + message);
    }

    public static void log(Object message) {
        if (message == null) {
            System.out.println("reference: null");
            return;
        }
        System.out.println("reference: " + message);
    }

    public static void flush() {
        typeSwitcher("");
        System.out.print(accumulatedStr);
        accumulatedStr = "";
        accumulatedSum = 0;
        previousStr = "";
        prevTypeName = "";
    }
}
