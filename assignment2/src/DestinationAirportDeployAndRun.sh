#!/usr/bin/env bash

echo "Transfering data to the Destination Airport node."
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp test/out/destinationAirportDir.zip sd101@l040101-ws03.ua.pt:test/AirLift
echo "Decompressing data sent to the Destination airport node."
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'cd test/AirLift ; unzip -uq destinationAirportDir.zip'
echo "Executing program at the destination airport node."
sshpass -f password ssh sd101@l040101-ws03.ua.pt 'cd test/AirLift/destinationAirportDir ; java ServerSide.main.DestinationAirportMain 22102 l040101-ws01.ua.pt 22100'
