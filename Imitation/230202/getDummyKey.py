import usb.core
import usb.util
import os
import usb.backend.libusb1
import time

#Func
def send(command, ep_out, ep_in):
	dev.write(ep_out, command)
	try:
		res = dev.read(ep_in, 100000, 1000)
		print(res)
	except:
		pass

#main
backend = usb.backend.libusb1.get_backend()
dev = usb.core.find(idVendor=0x04E8, idProduct=0x6860, backend=backend)
cfg = dev.get_active_configuration()
intf = cfg[(0, 0)]
ep_out = intf[1]
ep_in = intf[0]

print("[*]mtp-connection")
os.system('mtp-connect')

print("[*]Start App")
send(bytearray.fromhex("10000000010003950a00000003000000"), ep_out, ep_in)
send(bytearray.fromhex("1b000000020003950a000000010000000000000000000000010000"), ep_out, ep_in)
send(bytearray.fromhex("10000000010001950f000000ffffffff"), ep_out, ep_in)
send(bytearray.fromhex("e7000000020001950f00000066347171366a344f627841424f3171336d6932643650366e5262544a77645244653951347936305a6b726f596b6865654636614b645a556569504f38766252647a5559376f4a35610a6d7059486a3048354a665a52786c363357334c324b4d496d714d6d4762384f4666366942353169686a65516d6d6248786748457365775952634f354f3165574f4e376c612f732b720a37474757324d464f593972752b316957683465443435476c5037446d344c2f4c30336d4346586977566a4e445377454c757a31664b5153684c5a4a336a73704a5676554c34513d3d0a"), ep_out, ep_in)

time.sleep(3)
print("[*]Request DummyKey")
send(bytearray.fromhex("0c0000000100029511000000"), ep_out, ep_in)