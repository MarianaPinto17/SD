echo "Transfering data to the general repository node."
sshpass -f password ssh ruib@l040101-ws07.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh ruib@l040101-ws07.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp generalRepositoryDir.zip ruib@l040101-ws07.ua.pt:test/AirLift
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh ruib@l040101-ws07.ua.pt 'cd test/AirLift ; unzip -uq generalRepositoryDir.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh ruib@l040101-ws07.ua.pt 'cd test/AirLift/generalRepositoryDir ; java serverSide.main.GeneralRepositoryMain 22000'
echo "Server shutdown."
sshpass -f password ssh ruib@l040101-ws07.ua.pt 'cd test/AirLift/generalRepositoryDir ; less stat'


