
package com.golthiware.g13.controller;

import com.google.common.base.Preconditions;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import org.usb4java.Device;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 *
 */
public class HardwareG13 implements G13 {

    private final Device device;
    private final DeviceHandle handle;
    private final boolean detached;
    private final int interfaceNumber;

    public HardwareG13(
            Device device,
            DeviceHandle handle,
            boolean detached,
            int interfaceNumber) {
        this.device = device;
        this.handle = handle;
        this.detached = detached;
        this.interfaceNumber = interfaceNumber;
    }

    @Override
    public void setColor(int red, int green, int blue) {
        Preconditions.checkArgument(
                red >= 0 && red <= 255,
                "Red must be in [0 and 255], but " + red + " was received"
        );
        Preconditions.checkArgument(
                green >= 0 && green <= 255,
                "Green must be in [0 and 255], but " + green + " was received"
        );
        Preconditions.checkArgument(
                blue >= 0 && blue <= 255,
                "Blue must be in [0 and 255], but " + blue + " was received"
        );
        setColor((byte) red, (byte) green, (byte) blue);
    }

    private void setColor(byte red, byte green, byte blue) {
        final int messageLenght = 5;
        ByteBuffer buffer = ByteBuffer.allocateDirect(messageLenght);
        buffer.put((byte)5);
        buffer.put(red);
        buffer.put(green);
        buffer.put(blue);
        buffer.put((byte)0);
        
        buffer.rewind();
        
        int transfered = LibUsb.controlTransfer(
                handle,
                (byte) (LibUsb.REQUEST_TYPE_CLASS | LibUsb.RECIPIENT_INTERFACE),
                (byte) 9, 
                (short) 0x307, 
                (short) 0, 
                buffer, 
                1000
        );
        if (transfered < 0) {
            throw new LibUsbException("Control transfer failed", transfered);
        }
        if (transfered != messageLenght) {
            throw new RuntimeException("Not all data was sent to device");
        }
    }
    
    @Override
    public G13State read() throws InterruptedException {
        IntBuffer size = IntBuffer.allocate(1);
        ByteBuffer data = ByteBuffer.allocateDirect(Constants.G13_REPORT_SIZE);
        int errorCode = 0;
        boolean success = false;
        while (!success) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            errorCode = LibUsb.interruptTransfer(
                    handle,
                    (byte) (LibUsb.ENDPOINT_IN | Constants.G13_KEY_ENDPOINT),
                    data,
                    size, 
                    1000
            );
            success = errorCode == 0;
            if (!success) {
                if (errorCode == LibUsb.ERROR_TIMEOUT) {
                    if (data.position() != 0) {
                        throw new IllegalStateException("The call was "
                                + "interrupted in the middle of a read action");
                    }
                }
                else {
                    throw new LibUsbException("Control transfer failed", errorCode);
                }
            }
        }
        assert success;
     
        byte byte0 = data.get();
        int stickX = data.get() & 0xFF;
        int stickY = data.get() & 0xFF;
        
        System.out.println(byte0);
        
        return new G13State(parseKeys(BitSet.valueOf(data)), stickX, stickY);
    }
    
    private Set<G13Key> parseKeys(BitSet keysData) {
        EnumSet<G13Key> keys = EnumSet.noneOf(G13Key.class);

        for (G13Key key : G13Key.values()) {
            if (keysData.get(key.getIndex())) {
                keys.add(key);
            }
        }
        
        return keys;
    }

    @Override
    public void close() {
        releaseInterface(handle, interfaceNumber);
        if (detached) {
            reattach(handle, interfaceNumber);
        }
        LibUsb.close(handle);
    }

    private static DeviceHandle getDeviceHandle(Device device) {

        DeviceHandle handle = new DeviceHandle();
        int result = LibUsb.open(device, handle);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to open G13 device", result);
        }
        return handle;
    }

    /**
     * @param handle
     * @param interfaceNumber
     * @return true if the kernel driver has been detached
     */
    private static boolean detach(DeviceHandle handle, int interfaceNumber) {
        int result;

        // Check if kernel driver must be detached
        if (LibUsb.kernelDriverActive(handle, interfaceNumber) == 0) {
            return false;
        }
        if (!LibUsb.hasCapability(LibUsb.CAP_SUPPORTS_DETACH_KERNEL_DRIVER)) {

        }
        result = LibUsb.detachKernelDriver(handle, interfaceNumber);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException(
                    "Unable to detach kernel driver",
                    result
            );
        }
        return true;
    }

    private static void claimInterface(DeviceHandle handle, int interfaceNumber) {
        int result = LibUsb.claimInterface(handle, interfaceNumber);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to claim interface", result);
        }
    }

    private static void releaseInterface(DeviceHandle handle, int interfaceNumber) {
        int result = LibUsb.releaseInterface(handle, interfaceNumber);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to release interface", result);
        }
    }

    private static void reattach(DeviceHandle handle, int interfaceNumber) {
        int result = LibUsb.attachKernelDriver(handle, interfaceNumber);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException(
                    "Unable to re-attach kernel driver",
                    result
            );
        }
    }

    public static class Builder {

        private final Device device;

        public Builder(Device device) {
            this.device = device;
        }

        public G13 build() {
            int interfaceNumber = 0; //why this one? mistery

            DeviceHandle handle = getDeviceHandle(device);
            boolean detached = detach(handle, interfaceNumber);

            claimInterface(handle, interfaceNumber);

            return new HardwareG13(device, handle, detached, interfaceNumber);
        }

        @Override
        public String toString() {
            return String.format(
                    "Bus %03d, Device %03d",
                    LibUsb.getBusNumber(device),
                    LibUsb.getDeviceAddress(device));
        }
    }


}
