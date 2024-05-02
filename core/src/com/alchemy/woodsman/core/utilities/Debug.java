package com.alchemy.woodsman.core.utilities;

public class Debug {

    public static void logNormal(String label, String message) {
        String combinedMessage =  "\033[1;33m" + "> <Woodsman> " + label + ": " + message + "\n";
        System.out.print(combinedMessage);
    }

    public static void logState(String label, String message) {
        String combinedMessage = "\033[1;33m" + "> <Woodsman> " + "\033[1;33m" + label + ": " + message + "\n";
        System.out.print(combinedMessage);
    }

    public static void logError(String message) {
        String combinedMessage = "\033[1;33m" + "> <Woodsman> " + "\033[1;31m" + "Error" + "\033[0m"+ ": " + message + ", " + "\033[1;31m" + getCurrentRoot() + "." + getCurrentCaller() + "." + getCurrentMethod() + "\n";
        System.out.print(combinedMessage);
    }

    public static void logWarning(String message)
    {
        String combinedMessage = "\033[1;33m" + "> <Woodsman> " + "\033[1;31m" + "Warning" + "\033[0m"+ ": " + message + ", " + "\033[1;31m" + getCurrentRoot() + "." + getCurrentCaller() + "." + getCurrentMethod() + "\n";
        System.out.print(combinedMessage);
    }

    public static void logAlert(String message)
    {
        String combinedMessage = "\033[1;33m" + "> <Woodsman> " + "\033[1;32m" + "Alert" + "\033[0m"+ ": " + message + ", " + "\033[1;32m" + "Caller." + getCurrentCaller() + "\n";
        System.out.print(combinedMessage);
    }

    public static String getCurrentMethod() {
        String methodName = StackWalker.getInstance().walk(s -> s.skip(1).findFirst()).get().getMethodName();

        return methodName;
    }

    public static String getCurrentCaller() {
        String callerName = StackWalker.getInstance().walk(s -> s.skip(2).findFirst()).get().getMethodName();

        return callerName;
    }

    public static String getCurrentRoot() {
        String rootName = StackWalker.getInstance().walk(s -> s.skip(3).findFirst()).get().getMethodName();

        return rootName;
    }
}
