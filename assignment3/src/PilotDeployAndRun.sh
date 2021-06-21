#!/usr/bin/env bash

echo "Transfering data to the Pilot node."
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirPilot.zip sd101@l040101-ws05.ua.pt:test/AirLift
echo "Decompressing data sent to the Pilot node."
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'cd test/AirLift ; unzip -uq dirPilot.zip'
echo "Executing program at the pilot node."
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'cd test/AirLift/dirPilot ; ./Pilot_com_d.sh '
