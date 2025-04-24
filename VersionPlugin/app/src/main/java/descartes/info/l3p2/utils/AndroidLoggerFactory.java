package descartes.info.l3p2.utils;

import com.tencent.shadow.core.common.ILoggerFactory;
import com.tencent.shadow.core.common.Logger;

import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AndroidLoggerFactory implements ILoggerFactory {

    private static final int LOG_LEVEL_TRACE = 5;
    private static final int LOG_LEVEL_DEBUG = 4;
    private static final int LOG_LEVEL_INFO  = 3;
    private static final int LOG_LEVEL_WARN  = 2;
    private static final int LOG_LEVEL_ERROR = 1;

    private static AndroidLoggerFactory sInstance = new AndroidLoggerFactory();

    public static ILoggerFactory getInstance() {
        return sInstance;
    }

    final private ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<>();

    @Override
    public Logger getLogger(String name) {
        Logger simpleLogger = loggerMap.get(name);
        if (simpleLogger == null) {
            Logger newInstance = new AndroidLogger(name);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            simpleLogger = oldInstance == null ? newInstance : oldInstance;
        }
        return simpleLogger;
    }

    class AndroidLogger implements Logger {
        private final String name;
        private int level = LOG_LEVEL_INFO;

        AndroidLogger(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isTraceEnabled() {
            return level >= LOG_LEVEL_TRACE;
        }

        @Override
        public void trace(String msg) {
            if (isTraceEnabled()) {
                android.util.Log.v(name, msg);
            }
        }

        @Override
        public void trace(String format, Object arg) {
            if (isTraceEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                android.util.Log.v(name, ft.getMessage());
            }
        }

        @Override
        public void trace(String format, Object arg1, Object arg2) {
            if (isTraceEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                android.util.Log.v(name, ft.getMessage());
            }
        }

        @Override
        public void trace(String format, Object... arguments) {
            if (isTraceEnabled()) {
                FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
                android.util.Log.v(name, ft.getMessage());
            }
        }

        @Override
        public void trace(String msg, Throwable t) {
            if (isTraceEnabled()) {
                android.util.Log.v(name, msg, t);
            }
        }

        @Override
        public boolean isDebugEnabled() {
            return level >= LOG_LEVEL_DEBUG;
        }

        @Override
        public void debug(String msg) {
            if (isDebugEnabled()) {
                android.util.Log.d(name, msg);
            }
        }

        @Override
        public void debug(String format, Object arg) {
            if (isDebugEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                android.util.Log.d(name, ft.getMessage());
            }
        }

        @Override
        public void debug(String format, Object arg1, Object arg2) {
            if (isDebugEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                android.util.Log.d(name, ft.getMessage());
            }
        }

        @Override
        public void debug(String format, Object... arguments) {
            if (isDebugEnabled()) {
                FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
                android.util.Log.d(name, ft.getMessage());
            }
        }

        @Override
        public void debug(String msg, Throwable t) {
            if (isDebugEnabled()) {
                android.util.Log.d(name, msg, t);
            }
        }

        @Override
        public boolean isInfoEnabled() {
            return level >= LOG_LEVEL_INFO;
        }

        @Override
        public void info(String msg) {
            if (isInfoEnabled()) {
                android.util.Log.i(name, msg);
            }
        }

        @Override
        public void info(String format, Object arg) {
            if (isInfoEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                android.util.Log.i(name, ft.getMessage());
            }
        }

        @Override
        public void info(String format, Object arg1, Object arg2) {
            if (isInfoEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                android.util.Log.i(name, ft.getMessage());
            }
        }

        @Override
        public void info(String format, Object... arguments) {
            if (isInfoEnabled()) {
                FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
                android.util.Log.i(name, ft.getMessage());
            }
        }

        @Override
        public void info(String msg, Throwable t) {
            if (isInfoEnabled()) {
                android.util.Log.i(name, msg, t);
            }
        }

        @Override
        public boolean isWarnEnabled() {
            return level >= LOG_LEVEL_WARN;
        }

        @Override
        public void warn(String msg) {
            if (isWarnEnabled()) {
                android.util.Log.w(name, msg);
            }
        }

        @Override
        public void warn(String format, Object arg) {
            if (isWarnEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                android.util.Log.w(name, ft.getMessage());
            }
        }

        @Override
        public void warn(String format, Object arg1, Object arg2) {
            if (isWarnEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                android.util.Log.w(name, ft.getMessage());
            }
        }

        @Override
        public void warn(String format, Object... arguments) {
            if (isWarnEnabled()) {
                FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
                android.util.Log.w(name, ft.getMessage());
            }
        }

        @Override
        public void warn(String msg, Throwable t) {
            if (isWarnEnabled()) {
                android.util.Log.w(name, msg, t);
            }
        }

        @Override
        public boolean isErrorEnabled() {
            return level >= LOG_LEVEL_ERROR;
        }

        @Override
        public void error(String msg) {
            if (isErrorEnabled()) {
                android.util.Log.e(name, msg);
            }
        }

        @Override
        public void error(String format, Object arg) {
            if (isErrorEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                android.util.Log.e(name, ft.getMessage());
            }
        }

        @Override
        public void error(String format, Object arg1, Object arg2) {
            if (isErrorEnabled()) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                android.util.Log.e(name, ft.getMessage());
            }
        }

        @Override
        public void error(String format, Object... arguments) {
            if (isErrorEnabled()) {
                FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
                android.util.Log.e(name, ft.getMessage());
            }
        }

        @Override
        public void error(String msg, Throwable t) {
            if (isErrorEnabled()) {
                android.util.Log.e(name, msg, t);
            }
        }
    }

    static class FormattingTuple {
        static final FormattingTuple NULL = new FormattingTuple(null);
    
        private String message;
        private Throwable throwable;
        private Object[] argArray;
    
        FormattingTuple(String message) {
            this(message, null, null);
        }
    
        FormattingTuple(String message, Object[] argArray, Throwable throwable) {
            this.message = message;
            this.throwable = throwable;
            this.argArray = argArray;
        }
    
        String getMessage() {
            return message;
        }
    
        Throwable getThrowable() {
            return throwable;
        }
    
        Object[] getArgArray() {
            return argArray;
        }
    }

    static class MessageFormatter {
        static final char DELIM_START = '{';
        static final char DELIM_STOP = '}';
        static final String DELIM_STR = "{}";
        private static final char ESCAPE_CHAR = '\\';

        static FormattingTuple format(String messagePattern, Object arg) {
            return arrayFormat(messagePattern, new Object[]{arg});
        }

        static FormattingTuple format(String messagePattern, Object arg1, Object arg2) {
            return arrayFormat(messagePattern, new Object[]{arg1, arg2});
        }

        static FormattingTuple arrayFormat(String messagePattern, Object[] argArray) {
            if (messagePattern == null) {
                return new FormattingTuple(null);
            }
            if (argArray == null) {
                return new FormattingTuple(messagePattern);
            }

            int i = 0;
            int j;
            StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);

            for (int L = 0; L < argArray.length; L++) {
                j = messagePattern.indexOf(DELIM_STR, i);

                if (j == -1) {
                    if (i == 0) {
                        return new FormattingTuple(messagePattern, argArray, null);
                    } else {
                        sbuf.append(messagePattern.substring(i, messagePattern.length()));
                        return new FormattingTuple(sbuf.toString(), argArray, null);
                    }
                } else {
                    if (isEscapedDelimiter(messagePattern, j)) {
                        if (!isDoubleEscaped(messagePattern, j)) {
                            L--;
                            sbuf.append(messagePattern.substring(i, j - 1));
                            sbuf.append(DELIM_START);
                            i = j + 1;
                        } else {
                            sbuf.append(messagePattern.substring(i, j - 1));
                            deeplyAppendParameter(sbuf, argArray[L], new HashSet<>());
                            i = j + 2;
                        }
                    } else {
                        sbuf.append(messagePattern.substring(i, j));
                        deeplyAppendParameter(sbuf, argArray[L], new HashSet<>());
                        i = j + 2;
                    }
                }
            }
            sbuf.append(messagePattern.substring(i, messagePattern.length()));
            return new FormattingTuple(sbuf.toString(), argArray, null);
        }

        static boolean isEscapedDelimiter(String messagePattern, int delimiterStartIndex) {
            if (delimiterStartIndex == 0) {
                return false;
            }
            char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
            return potentialEscape == ESCAPE_CHAR;
        }

        static boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
            return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == ESCAPE_CHAR;
        }

        private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Set<Object[]> seenSet) {
            if (o == null) {
                sbuf.append("null");
                return;
            }
            if (!o.getClass().isArray()) {
                safeObjectAppend(sbuf, o);
            } else {
                if (o instanceof boolean[]) {
                    booleanArrayAppend(sbuf, (boolean[]) o);
                } else if (o instanceof byte[]) {
                    byteArrayAppend(sbuf, (byte[]) o);
                } else if (o instanceof char[]) {
                    charArrayAppend(sbuf, (char[]) o);
                } else if (o instanceof short[]) {
                    shortArrayAppend(sbuf, (short[]) o);
                } else if (o instanceof int[]) {
                    intArrayAppend(sbuf, (int[]) o);
                } else if (o instanceof long[]) {
                    longArrayAppend(sbuf, (long[]) o);
                } else if (o instanceof float[]) {
                    floatArrayAppend(sbuf, (float[]) o);
                } else if (o instanceof double[]) {
                    doubleArrayAppend(sbuf, (double[]) o);
                } else {
                    objectArrayAppend(sbuf, (Object[]) o, seenSet);
                }
            }
        }

        private static void safeObjectAppend(StringBuilder sbuf, Object o) {
            try {
                String oAsString = o.toString();
                sbuf.append(oAsString);
            } catch (Throwable t) {
                sbuf.append("[FAILED toString()]");
            }
        }

        private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Set<Object[]> seenSet) {
            sbuf.append('[');
            if (!seenSet.contains(a)) {
                seenSet.add(a);
                final int len = a.length;
                for (int i = 0; i < len; i++) {
                    deeplyAppendParameter(sbuf, a[i], seenSet);
                    if (i != len - 1)
                        sbuf.append(", ");
                }
                seenSet.remove(a);
            } else {
                sbuf.append("...");
            }
            sbuf.append(']');
        }

        private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void charArrayAppend(StringBuilder sbuf, char[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void intArrayAppend(StringBuilder sbuf, int[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void longArrayAppend(StringBuilder sbuf, long[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }

        private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
            sbuf.append('[');
            final int len = a.length;
            for (int i = 0; i < len; i++) {
                sbuf.append(a[i]);
                if (i != len - 1)
                    sbuf.append(", ");
            }
            sbuf.append(']');
        }
    }
}

