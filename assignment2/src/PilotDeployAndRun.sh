echo "Transfering data to the Pilot node."
sshpass -f password ssh @ 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp pilotDir.zip ruib@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the Pilot node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq pilotDir.zip'
echo "Executing program at the pilot node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift/pilotDir ; java serverSide.main.PilotMain l040101-ws06.ua.pt 22001 l040101-ws07.ua.pt 22000 stat 3'
