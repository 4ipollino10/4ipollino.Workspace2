package ru.gulyaev.factory.lab4.threadpool;

import org.apache.log4j.Logger;
import ru.gulyaev.factory.lab4.task.Task;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;


public class ThreadPool {
    public static final Logger log = Logger.getLogger(ThreadPool.class);

    private static final String STARTED = "Started ";
    private static final String THREADS_IN = " threads in ";
    private static final String SPACE = " ";
    private static final String JOINED = " Joined!";
    private final String threadPoolName;

    private volatile int delay;

    private final Queue<Task> taskQueue = new ArrayDeque<>();

    private final Set<PooledThread> availableThreads = new LinkedHashSet<>();

    public ThreadPool(int threadCount, String threadPoolName, String pooledThreadName, int startDelay) {
        this.delay = startDelay;
        this.threadPoolName = threadPoolName;
        for (int i = 0; i < threadCount; i++) {
            availableThreads.add(new PooledThread(pooledThreadName + SPACE + i, this));
        }
        for (PooledThread pt: availableThreads) {
            pt.start();
        }

        log.info(STARTED + threadCount + THREADS_IN + threadPoolName);
    }


    public synchronized void addTask(Task t) {
        taskQueue.add(t);
        notify();
    }

    public void shutdown() {
        for (PooledThread pt : availableThreads) {
            pt.interruptPooledThread();
            try {
                pt.join();
                log.info(pt.getName() + JOINED);
            } catch (InterruptedException e) {
                log.info(threadPoolName + Constants.INTERRUPTED);
            }
        }
    }

    public void setDelay(int newDelay) {
        this.delay = newDelay;
    }

    public int getDelay() {
        return delay;
    }

    public synchronized Task getTask() throws InterruptedException {
        while (taskQueue.size() < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.info(threadPoolName + Constants.INTERRUPTED);
                throw e;
            }
        }
        Task t = taskQueue.remove();
        notify();
        return t;
    }
}