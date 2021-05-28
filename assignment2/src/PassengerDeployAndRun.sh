#!/usr/bin/env bash

echo "Transfering data to the Passenger node."
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp test/out/passengerDir.zip sd101@l040101-ws07.ua.pt:test/AirLift
echo "Decompressing data sent to the passenger node."
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'cd test/AirLift ; unzip -uq passengerDir.zip'
echo "Executing program at the passenger node."
#                                                                                                                   DepAir: Hostname   Port  DesAir: Hostname   Port  Plane: Hostname    Port
sshpass -f password ssh sd101@l040101-ws07.ua.pt 'cd test/AirLift/passengerDir ; java ClientSide.main.PassengerMain l040101-ws02.ua.pt 22101 l040101-ws03.ua.pt 22102 l040101-ws04.ua.pt 22103'
