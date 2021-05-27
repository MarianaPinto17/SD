echo "Transfering data to the Passenger node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp passengerDir.zip sd101@l040101-ws01.ua.pt:test/AirLift
echo "Decompressing data sent to the passenger node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift ; unzip -uq passengerDir.zip'
echo "Executing program at the passenger node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift/passengerDir ; java serverSide.main.PassengerMain l040101-ws01.ua.pt 22101 l040101-ws01.ua.pt 22100 stat 3'
