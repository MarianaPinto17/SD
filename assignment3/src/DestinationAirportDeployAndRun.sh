#!/usr/bin/env bash

echo "Transfering data to the Departure Airport node."
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws03.ua.pt  'rm -rf test/AirLift/*'
sshpass -f password scp dirDesAir.zip sd101@l040101-ws03.ua.pt:test/AirLift
echo "Decompressing data sent to the departure airport node."
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'cd test/AirLift ; unzip -uq dirDesAir.zip'
echo "Executing program at the departure airport node."
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'cd test/AirLift/dirDesAir ; ./DesAir_com_d.sh'

