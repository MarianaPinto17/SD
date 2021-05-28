#!/usr/bin/env bash

echo "Transfering data to the Hostess node."
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp test/out/hostessDir.zip sd101@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the hostess node."
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq hostessDir.zip'
echo "Executing program at the hostess node."
#                                                                                                         DepAir: Hostname   Port  Plane: Hostname    Port
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'cd test/AirLift/hostessDir ; java ClientSide.main.HostessMain l040101-ws02.ua.pt 22101 l040101-ws04.ua.pt 22103'
