
package com.golthiware.g13.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 *
 */
public class G13Finder implements AutoCloseable {

    private static final Logger LOG = Logger.getLogger(G13Finder.class.getName());
    private final Context context = initializeContext();
    
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
    
    
    @Nonnull
    public List<HardwareG13.Builder> getG13Builders() {
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

    @Override
    public void close() {
        // Deinitialize the libusb context
        LibUsb.exit(context);
    }
}
