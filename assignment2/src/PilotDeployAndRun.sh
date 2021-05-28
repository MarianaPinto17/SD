#!/usr/bin/env bash

echo "Transfering data to the Pilot node."
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp test/out/pilotDir.zip sd101@l040101-ws05.ua.pt:test/AirLift
echo "Decompressing data sent to the Pilot node."
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'cd test/AirLift ; unzip -uq pilotDir.zip'
echo "Executing program at the pilot node."
#                                                                                                           DepAir: Hostname   Port  DestAir: Hostname  Port  Plane: Hostname    Port  Repos: Hostname    Port 
sshpass -f password ssh sd101@l040101-ws05.ua.pt 'cd test/AirLift/pilotDir ; java ClientSide.main.PilotMain l040101-ws02.ua.pt 22101 l040101-ws03.ua.pt 22102 l040101-ws04.ua.pt 22103 l040101-ws01.ua.pt 22100'
