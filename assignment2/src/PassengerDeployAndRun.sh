echo "Transfering data to the Passenger node."
sshpass -f password ssh @ 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp passengerDir.zip ruib@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the passenger node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq passengerDir.zip'
echo "Executing program at the passenger node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift/passengerDir ; java serverSide.main.PassengerMain l040101-ws06.ua.pt 22001 l040101-ws07.ua.pt 22000 stat 3'
