echo "Transfering data to the Pilot node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp pilotDir.zip sd101@l040101-ws01.ua.pt:test/AirLift
echo "Decompressing data sent to the Pilot node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift ; unzip -uq pilotDir.zip'
echo "Executing program at the pilot node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift/pilotDir ; java serverSide.main.PilotMain l040101-ws01.ua.pt 22101 l040101-ws01.ua.pt22100 stat 3'
