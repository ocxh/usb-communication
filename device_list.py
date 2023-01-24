import usb.core

dev = usb.core.find(find_all=True)

for d in dev:
  print("VID="+str(hex(d.idVendor))+" | "+"PID="+str(hex(d.idProduct)))