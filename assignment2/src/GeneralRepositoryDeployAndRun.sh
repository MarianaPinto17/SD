echo "Transfering data to the general repository node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp generalRepositoryDir.zip sd101@l040101-ws01.ua.pt:test/AirLift
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift ; unzip -uq generalRepositoryDir.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift/generalRepositoryDir ; java serverSide.main.GeneralRepositoryMain 22100'
echo "Server shutdown."
sshpass -f password ssh sd101@l040101-ws01.ua.pt 'cd test/AirLift/generalRepositoryDir ; less stat'


