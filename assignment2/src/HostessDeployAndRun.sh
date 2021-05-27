echo "Transfering data to the Hostess node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp hostessDir.zip sd101@l040101-ws01.ua.pt:test/AirLift
echo "Decompressing data sent to the hostess node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift ; unzip -uq hostessDir.zip'
echo "Executing program at the hostess node."
sshpass -f password ssh l040101-ws01.ua.pt 'cd test/AirLift/hostessDir ; java serverSide.main.HostessMain l040101-ws01.ua.pt 22101 l040101-ws01.ua.pt 22100 stat 3'
