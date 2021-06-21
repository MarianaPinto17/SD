#!/usr/bin/env bash

echo "Transfering data to the general repository node."
sshpass -f password ssh sd101@l040101-ws08.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws08.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirGeneralRepos.zip sd101@l040101-ws08.ua.pt:test/AirLift
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd101@l040101-ws08.ua.pt 'cd test/AirLift ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh sd101@l040101-ws08.ua.pt 'cd test/AirLift/dirGeneralRepos ; ./repos_com_d.sh sd101'
echo "Server shutdown."
sshpass -f password ssh sd101@l040101-ws08.ua.pt 'cd test/AirLift/dirGeneralRepos ; less stat'
