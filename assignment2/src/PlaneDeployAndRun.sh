echo "Transfering data to the Plane node."
sshpass -f password ssh @ 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp planeDir.zip ruib@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the Plane node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq planeDir.zip'
echo "Executing program at the plane node."
sshpass -f password ssh ruib@l040101-ws06.ua.pt 'cd test/AirLift/planeDir ; java serverSide.main.PlaneMain 22001 l040101-ws07.ua.pt 22000'
