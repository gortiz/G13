
package com.golthiware.g13.controller;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.annotation.concurrent.NotThreadSafe;

/**
 *
 */
@NotThreadSafe
public class G13Poller {

    private final G13 g13;
    private final long pollPeriod;
    private final Consumer<G13Diff> consumer;
    private boolean running;
    private Thread pollerThread;
    private final static AtomicInteger ID_PROVIDER = new AtomicInteger(0);

    public G13Poller(G13 g13, long pollPeriod, Consumer<G13Diff> consumer) {
        this.g13 = g13;
        this.pollPeriod = pollPeriod;
        this.consumer = consumer;
    }

    public void start() {
        running = true;
        pollerThread = new Thread(() -> {
            G13State oldState = G13State.REPOSE_STATE;
            G13State newState;
            while (running) {
                try {
                    newState = g13.read();
                    G13Diff diff = G13Diff.diff(oldState, newState);
                    consumer.accept(diff);
                    oldState = newState;

                    Thread.sleep(pollPeriod);
                }
                catch (InterruptedException ex) {
                }
            }
        });
        pollerThread.setDaemon(true);
        pollerThread.setName("G13 poller thread -" + ID_PROVIDER.getAndIncrement());
        pollerThread.start();
    }

    public void stop() {
        running = false;
        pollerThread.interrupt();
    }

    public void stopAndWait() throws InterruptedException {
        stop();
        pollerThread.join();
    }
}
