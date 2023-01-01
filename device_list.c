#include <libusb-1.0/libusb.h>
#include <stdio.h>

static void print_Dlist(libusb_device **devs)
{
        libusb_device *dev;
        int i = 0, j = 0;
        uint8_t path[8]; 

        while ((dev = devs[i++]) != NULL) {
                struct libusb_device_descriptor desc;
                int r = libusb_get_device_descriptor(dev, &desc);

                printf("VID: %04x | PID: %04x | Interface: %d | Device Addr: %d",
                        desc.idVendor, desc.idProduct,
                        libusb_get_bus_number(dev), 
libusb_get_device_address(dev));
        }
}

int main(void)
{
        libusb_device **devs;
        int r;
        ssize_t cnt;

        r = libusb_init(NULL);

        //get devices
        cnt = libusb_get_device_list(NULL, &devs);
        if (cnt < 0){
                libusb_exit(NULL);
                return (int) cnt;
        }
        //print list
        print_Dlist(devs);
        libusb_free_device_list(devs, 1);

        libusb_exit(NULL);
        return 0;
