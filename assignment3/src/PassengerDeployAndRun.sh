#!/usr/bin/env bash

echo "Transfering data to the Passenger node."
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirPassengers.zip sd101@l040101-ws07.ua.pt:test/AirLift
echo "Decompressing data sent to the passenger node."
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'cd test/AirLift ; unzip -uq dirPassengers.zip'
echo "Executing program at the passenger node."
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'cd test/AirLift/dirPassengers ; ./Passengers_com_d.sh'
