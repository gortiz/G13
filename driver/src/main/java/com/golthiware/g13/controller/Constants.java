
package com.golthiware.g13.controller;

import java.nio.ByteBuffer;

/**
 *
 */
public class Constants {
    
    public static final short G13_INTERFACE = 0;
    public static final byte G13_KEY_ENDPOINT = 1;
    public static final short G13_LCD_ENDPOINT = 2;
    public static final short G13_KEY_READ_TIMEOUT = 0;
    public static final short G13_VENDOR_ID = 0x046d;
    public static final short G13_PRODUCT_ID = -15844; //0xc21c
    public static final short G13_REPORT_SIZE = 8;
    public static final short G13_LCD_BUFFER_SIZE = 0x3c0;
    public static final short G13_NUM_KEYS = 40;
    static {
        ByteBuffer byteBuffer = ByteBuffer
                .allocate(2)
                .put(Byte.parseByte("0xc2"))
                .put(Byte.parseByte("0x1c"));
        byteBuffer.getInt();
    }
    
    private Constants() {}
    
}
