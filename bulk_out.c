#include <libusb-1.0/libusb.h>
#include <assert.h>
#include <stdio.h>
#include <string.h>

#define BULK_OUT 0x02

#define VID 0x0000
#define PID 0x0000
 
int main()
{
   libusb_init(NULL);
   libusb_device_handle *handle;
   handle = libusb_open_device_with_vid_pid(NULL, VID, PID);

   libusb_set_configuration(handle, 1);
   if(libusb_kernel_driver_active(handle, 0)==1){
        libusb_detach_kernel_driver(handle, 0);
   }
	//set interface(usbmon)
	libusb_claim_interface(handle, 3);

   char request[] = "bulk out!"

	//BULK OUT send data
   int bytes_transferred;

   int resultCode = libusb_bulk_transfer(handle, BULK_OUT, request, sizeof(request), &bytes_transffered,0);
   if(resultCode==0){printf("Data sent");}else{printf("Data couldn't be send");}
	
	//exit
   libusb_close(handle);
}