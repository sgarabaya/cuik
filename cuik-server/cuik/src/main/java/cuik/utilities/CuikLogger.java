package cuik.utilities;

import java.util.concurrent.ConcurrentLinkedQueue;

enum LogLevel {
    Debug,
    Info,
    Error
}

record Log(LogLevel level, String message) {
}

public abstract class CuikLogger {
    private static ConcurrentLinkedQueue<Log> logsQueue = new ConcurrentLinkedQueue<>();

    public static void debug(String format, Object... params) {
        logsQueue.add(new Log(LogLevel.Debug, String.format(format, params)));
    }

    public static void info(String format, Object... params) {
        logsQueue.add(new Log(LogLevel.Info, String.format(format, params)));
    }

    public static void error(String format, Object... params) {
        logsQueue.add(new Log(LogLevel.Error, String.format(format, params)));
    }

    public static void error(Throwable ex) {
        var message = ex.getMessage();
        logsQueue.add(new Log(LogLevel.Error, message));
    }

    public static void flush() {
        while (!logsQueue.isEmpty()) {
            var log = logsQueue.poll();
            System.out.printf("[%s] %s\n", log.level(), log.message());
        }
    }
}
