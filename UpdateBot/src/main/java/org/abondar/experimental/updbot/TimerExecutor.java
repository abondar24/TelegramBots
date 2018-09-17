package org.abondar.experimental.updbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerExecutor {

    private static volatile TimerExecutor instance;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static final Logger logger = LogManager.getLogger();
    private TimerExecutor(){}




    public static TimerExecutor getInstance(){
        final TimerExecutor currentInstance;
        if (instance == null) {
            synchronized (TimerExecutor.class) {
                if (instance == null) {
                    instance = new TimerExecutor();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }

        return currentInstance;
    }


    public void start(Task task) {
        logger.info("Starting: " +task.taskName);
        final Runnable taskWrapper = () -> {
            try {
                task.execute();
                start(task);
            } catch (Exception e) {
                logger.info( "Bot threw an unexpected exception at TimerExecutor:", e.getMessage());
            }
        };

        executorService.schedule(taskWrapper, 10, TimeUnit.SECONDS);
    }

    /**
     * Stop the thread
     */
    public void stop() {
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            logger.info("Stopped updates");
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
    }

    public  abstract class Task {

        private String taskName;

        public Task(String taskName) {
            this.taskName = taskName;
        }


        public abstract void execute();
    }
}
