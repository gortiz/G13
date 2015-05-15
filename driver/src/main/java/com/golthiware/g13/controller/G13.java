package com.golthiware.g13.controller;

/**
 *
 */
public interface G13 extends AutoCloseable {

    public G13State read() throws InterruptedException;
    public void setColor(int red, int green, int blue) throws InterruptedException;
}
