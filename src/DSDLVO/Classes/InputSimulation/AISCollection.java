package DSDLVO.Classes.InputSimulation;

import DSDLVO.Classes.InputManagement.AISData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AISCollection {
    private HashMap<Integer, AISData> collection = new HashMap<Integer, AISData>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public AISCollection() { }

    public void insert(AISData item) {
        lock.writeLock().lock();
        try {
            collection.put(item.mmsi, item);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public void insertList(List<AISData> list) {
        lock.writeLock().lock();
        try {
            list.forEach(item -> collection.put(item.mmsi, item));
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public AISData get(int mmsi) {
        lock.readLock().lock();
        try {
            return collection.get(mmsi);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public List<AISData> getAll() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(collection.values());
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public boolean contains(AISData item) {
        lock.readLock().lock();
        try {
            return collection.containsKey(item.mmsi);
        }
        finally {
            lock.readLock().unlock();
        }
    }
}
