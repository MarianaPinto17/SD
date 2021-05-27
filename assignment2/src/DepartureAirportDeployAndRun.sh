echo "Transfering data to the Departure Airport node."
sshpass -f password ssh @ 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp departureairportDir.zip ruib@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the departure airport node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq departureairportDir.zip'
echo "Executing program at the departure airport node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift/departureairportDir ; java serverSide.main.departureAirportMain 22001 l040101-ws07.ua.pt 22000'
