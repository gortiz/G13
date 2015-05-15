
package com.golthiware.g13.controller;

/**
 *
 */
public enum G13Key {
    /* byte 3 */
    G13_KEY_G1(0),
    G13_KEY_G2(1),
    G13_KEY_G3(2),
    G13_KEY_G4(3),

    G13_KEY_G5(4),
    G13_KEY_G6(5),
    G13_KEY_G7(6),
    G13_KEY_G8(7),

    /* byte 4 */
    G13_KEY_G9(8),
    G13_KEY_G10(9),
    G13_KEY_G11(10),
    G13_KEY_G12(11),

    G13_KEY_G13(12),
    G13_KEY_G14(13),
    G13_KEY_G15(14),
    G13_KEY_G16(15),

    /* byte 5 */
    G13_KEY_G17(16),
    G13_KEY_G18(17),
    G13_KEY_G19(18),
    G13_KEY_G20(19),

    G13_KEY_G21(20),
    G13_KEY_G22(21),
    G13_KEY_UNDEF1(22),
    G13_KEY_LIGHT_STATE(23),

    /* byte 6 */
    G13_KEY_BD(24),
    G13_KEY_L1(25),
    G13_KEY_L2(26),
    G13_KEY_L3(27),

    G13_KEY_L4(28),
    G13_KEY_M1(29),
    G13_KEY_M2(30),
    G13_KEY_M3(31),

    /* byte 7 */
    G13_KEY_MR(32),
    G13_KEY_LEFT(33),
    G13_KEY_DOWN(34),
    G13_KEY_TOP(35),

    G13_KEY_UNDEF3(36),
    G13_KEY_LIGHT(37),
    G13_KEY_LIGHT2(38),
    G13_KEY_MISC_TOGGLE(39);
    
    /**
     * The index in array buffer of the given key
     */
    private final int index;
    
    private G13Key(int key) {
        assert key == this.ordinal();
        this.index = key;
    }
    
    public int getIndex() {
        return index;
    }
    
    public int getBufferPosition() {
        return index / 8;
    }
    
    public int getMask() {
        return index % 8;
    }
}
