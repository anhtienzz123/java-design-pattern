 package creational.singleton.loggersystem;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Singleton Logger using Lazy Initialization Holder Pattern
 * Thread-safe, high-performance logging system with multiple log levels
 */
public class Logger {
    
    // Lazy initialization holder pattern - thread-safe without synchronization
    private static class LoggerHolder {
        private static final Logger INSTANCE = new Logger();
    }
    
    public enum LogLevel {
        DEBUG(0), INFO(1), WARN(2), ERROR(3), FATAL(4);
        
        private final int level;
        
        LogLevel(int level) {
            this.level = level;
        }
        
        public int getLevel() {
            return level;
        }
    }
    
    private final DateTimeFormatter timeFormatter;
    private final String logFileName;
    private final ConcurrentLinkedQueue<String> logBuffer;
    private final ScheduledExecutorService executorService;
    private LogLevel currentLogLevel;
    private boolean consoleOutput;
    private boolean fileOutput;
    private PrintWriter fileWriter;
    
    // Private constructor prevents external instantiation
    private Logger() {
        this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        this.logFileName = "application.log";
        this.logBuffer = new ConcurrentLinkedQueue<>();
        this.executorService = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "Logger-Thread");
            t.setDaemon(true); // Don't prevent JVM shutdown
            return t;
        });
        this.currentLogLevel = LogLevel.INFO;
        this.consoleOutput = true;
        this.fileOutput = true;
        
        initializeFileWriter();
        startLogProcessor();
        
        // Add shutdown hook to flush remaining logs
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }
    
    /**
     * Get the singleton instance
     */
    public static Logger getInstance() {
        return LoggerHolder.INSTANCE;
    }
    
    /**
     * Initialize file writer for log file output
     */
    private void initializeFileWriter() {
        try {
            fileWriter = new PrintWriter(new FileWriter(logFileName, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize log file writer: " + e.getMessage());
            fileOutput = false;
        }
    }
    
    /**
     * Start background log processor
     */
    private void startLogProcessor() {
        executorService.scheduleAtFixedRate(this::processLogBuffer, 100, 100, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Process queued log messages
     */
    private void processLogBuffer() {
        String logMessage;
        while ((logMessage = logBuffer.poll()) != null) {
            writeToOutputs(logMessage);
        }
        
        if (fileWriter != null) {
            fileWriter.flush();
        }
    }
    
    /**
     * Write log message to configured outputs
     */
    private void writeToOutputs(String message) {
        if (consoleOutput) {
            System.out.println(message);
        }
        
        if (fileOutput && fileWriter != null) {
            fileWriter.println(message);
        }
    }
    
    /**
     * Log message with specified level
     */
    private void log(LogLevel level, String message, Object... args) {
        if (level.getLevel() >= currentLogLevel.getLevel()) {
            String formattedMessage = formatLogMessage(level, message, args);
            logBuffer.offer(formattedMessage);
        }
    }
    
    /**
     * Format log message with timestamp and level
     */
    private String formatLogMessage(LogLevel level, String message, Object... args) {
        String timestamp = LocalDateTime.now().format(timeFormatter);
        String threadName = Thread.currentThread().getName();
        String formattedMessage = args.length > 0 ? String.format(message, args) : message;
        
        return String.format("[%s] [%s] [%s] %s", 
                timestamp, level.name(), threadName, formattedMessage);
    }
    
    // Public logging methods
    
    /**
     * Log debug message
     */
    public void debug(String message, Object... args) {
        log(LogLevel.DEBUG, message, args);
    }
    
    /**
     * Log info message
     */
    public void info(String message, Object... args) {
        log(LogLevel.INFO, message, args);
    }
    
    /**
     * Log warning message
     */
    public void warn(String message, Object... args) {
        log(LogLevel.WARN, message, args);
    }
    
    /**
     * Log error message
     */
    public void error(String message, Object... args) {
        log(LogLevel.ERROR, message, args);
    }
    
    /**
     * Log error with exception
     */
    public void error(String message, Throwable throwable, Object... args) {
        String formattedMessage = args.length > 0 ? String.format(message, args) : message;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        
        log(LogLevel.ERROR, formattedMessage + "\n" + sw.toString());
    }
    
    /**
     * Log fatal message
     */
    public void fatal(String message, Object... args) {
        log(LogLevel.FATAL, message, args);
    }
    
    // Configuration methods
    
    /**
     * Set current log level
     */
    public void setLogLevel(LogLevel level) {
        this.currentLogLevel = level;
        info("Log level changed to: %s", level.name());
    }
    
    /**
     * Get current log level
     */
    public LogLevel getLogLevel() {
        return currentLogLevel;
    }
    
    /**
     * Enable/disable console output
     */
    public void setConsoleOutput(boolean enabled) {
        this.consoleOutput = enabled;
        info("Console output %s", enabled ? "enabled" : "disabled");
    }
    
    /**
     * Enable/disable file output
     */
    public void setFileOutput(boolean enabled) {
        this.fileOutput = enabled;
        info("File output %s", enabled ? "enabled" : "disabled");
    }
    
    /**
     * Check if level is enabled for logging
     */
    public boolean isLevelEnabled(LogLevel level) {
        return level.getLevel() >= currentLogLevel.getLevel();
    }
    
    /**
     * Clear log file
     */
    public void clearLogFile() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
            
            // Recreate file writer with truncation
            fileWriter = new PrintWriter(new FileWriter(logFileName, false));
            info("Log file cleared");
        } catch (IOException e) {
            System.err.println("Failed to clear log file: " + e.getMessage());
        }
    }
    
    /**
     * Get log file name
     */
    public String getLogFileName() {
        return logFileName;
    }
    
    /**
     * Get buffer size (for monitoring)
     */
    public int getBufferSize() {
        return logBuffer.size();
    }
    
    /**
     * Force flush of log buffer
     */
    public void flush() {
        processLogBuffer();
    }
    
    /**
     * Shutdown logger and cleanup resources
     */
    public void shutdown() {
        info("Logger shutting down...");
        
        // Process remaining messages
        processLogBuffer();
        
        // Shutdown executor
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        // Close file writer
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
    
    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton instance cannot be cloned");
    }
} 