echo "Transfering data to the Destination Airport node."
sshpass -f password ssh @ 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp destinationAirportDir.zip ruib@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the Destination airport node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq destinationAirportDir.zip'
echo "Executing program at the destination airport node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift/destinationAirportDir ; java serverSide.main.destinationAirportMain 22001 l040101-ws07.ua.pt 22000'
