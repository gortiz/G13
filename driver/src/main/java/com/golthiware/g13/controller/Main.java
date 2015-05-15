package com.golthiware.g13.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceHandle;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 *
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        Context context = initializeContext();
        try {
            List<HardwareG13.Builder> g13Builders = getG13Builders(context);
            if (g13Builders.isEmpty()) {
                throw new RuntimeException("No G13 found");
            }
            if (g13Builders.size() > 1) {
                throw new RuntimeException("There are at least two G13 devices "
                        + "and only one can be managed. Devices are: "+g13Builders);
            }
            HardwareG13.Builder builder = g13Builders.get(0);
            LOG.log(Level.INFO, "One G13 found at {0}", builder);
            
            try (G13 g13 = builder.build()) {
                g13.setColor(255, 255, 255);
                while (true) {
                    G13State g13State = g13.read();
                    System.out.println(g13State);
                }
//                g13.setColor(100, 100, 100);
            }
        } finally {
            close(context);
        }

    }

    private static Context initializeContext() {
        // Create the libusb context
        Context context = new Context();

        // Initialize the libusb context
        int result = LibUsb.init(context);
        if (result < 0) {
            throw new LibUsbException("Unable to initialize libusb", result);
        }

        return context;
    }

    private static void close(Context context) {
        // Deinitialize the libusb context
        LibUsb.exit(context);
    }

    @Nonnull
    private static List<HardwareG13.Builder> getG13Builders(Context context) {
        // Read the USB device list
        DeviceList list = new DeviceList();
        int result = LibUsb.getDeviceList(context, list);
        if (result < 0) {
            throw new LibUsbException("Unable to get device list", result);
        }

        try {
            List<HardwareG13.Builder> g13s = new ArrayList<>(1);
            
            // Iterate over all devices and list them
            for (Device device : list) {
                int address = LibUsb.getDeviceAddress(device);
                int busNumber = LibUsb.getBusNumber(device);
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);
                if (result < 0) {
                    throw new LibUsbException(
                            "Unable to read device descriptor", result);
                }
                LOG.finest(() -> {
                    return String.format(
                            "Bus %03d, Device %03d: Vendor %04x, Product %04x",
                            busNumber,
                            address,
                            descriptor.idVendor(),
                            descriptor.idProduct()
                    );
                });
                if (descriptor.idVendor() == Constants.G13_VENDOR_ID
                        && descriptor.idProduct() == Constants.G13_PRODUCT_ID) {
                    g13s.add(new HardwareG13.Builder(device));
                }
            }
            return g13s;
        } finally {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);
        }
    }

    private static DeviceHandle getG13DeviceHandler(Device device) {
        DeviceHandle handle = new DeviceHandle();
        int result = LibUsb.open(device, handle);
        if (result != LibUsb.SUCCESS) {
            throw new LibUsbException("Unable to open USB device", result);
        }
        return handle;
    }
}
