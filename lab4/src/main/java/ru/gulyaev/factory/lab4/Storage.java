package ru.gulyaev.factory.lab4;
import lombok.extern.slf4j.Slf4j;
import ru.gulyaev.factory.lab4.gear.Gear;
import ru.gulyaev.factory.lab4.gear.Product;
import ru.gulyaev.factory.lab4.task.SupplyTask;
import ru.gulyaev.factory.lab4.thredpool.ThreadPool;

import java.util.ArrayDeque;

@Slf4j
public class Storage<T extends Product & Gear> {
    public static final String STORAGE_WAS_INTERRUPTED = "Storage was interrupted!";
    private final ArrayDeque<T> items;
    private final int storageCapacity;
    private final ThreadPool threadPool;
    private T t;


    public Storage(int capacity, ThreadPool threadPool, Class<T> productType) {
        storageCapacity = capacity;

        this.threadPool = threadPool;
        items = new ArrayDeque<>();
        t = null;
        try {
            t = productType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < capacity; i++) {
            threadPool.addTask(new SupplyTask(this, (Class<T>) t.getClass()));
        }
    }

    public double getOccupancy() {
        return items.size() / (double)storageCapacity;
    }

    public int getStorageCapacity() { return  storageCapacity; }

    public synchronized T get() throws InterruptedException {
        while (items.size() < 1) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                log.info(STORAGE_WAS_INTERRUPTED);
                throw e;
            }
        }
        T item = items.removeLast();
        threadPool.addTask(new SupplyTask(this, (Class<T>)t.getClass()));
        notify();
        return item;
    }

    public synchronized void put(T item) {
        while (items.size() >= storageCapacity) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                log.info(STORAGE_WAS_INTERRUPTED);
            }
        }

        items.add(item);
        notify();
    }
}
