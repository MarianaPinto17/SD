echo "Transfering data to the Hostess node."
sshpass -f password ssh @ 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp hostessDir.zip ruib@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the hostess node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq hostessDir.zip'
echo "Executing program at the hostess node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift/hostessDir ; java serverSide.main.HostessMain l040101-ws06.ua.pt 22001 l040101-ws07.ua.pt 22000 stat 3'
