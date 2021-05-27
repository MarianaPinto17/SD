echo "Transfering data to the Plane node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp planeDir.zip sd101@l040101-ws01.ua.pt:test/AirLift
echo "Decompressing data sent to the Plane node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift ; unzip -uq planeDir.zip'
echo "Executing program at the plane node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift/planeDir ; java serverSide.main.PlaneMain 22101 l040101-ws01.ua.pt 22100'
