package com.machine.thread;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Mutex implements Serializable {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean isHeldExclusively() {
            return this.getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (this.getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            this.setExclusiveOwnerThread(null);
            this.setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        this.sync.acquire(1);
    }

    public boolean tryLock() {
        return this.sync.tryAcquire(1);
    }

    public void unlock() {
        this.sync.tryRelease(1);
    }

    public boolean isLocked() {
        return this.sync.isHeldExclusively();
    }
}