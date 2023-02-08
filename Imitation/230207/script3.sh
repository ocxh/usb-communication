#!/system/bin/sh

while :
do
    find /sdcard/SmartSwitch/OtgBackupTemp -type d | wc -l
    sleep 1
done