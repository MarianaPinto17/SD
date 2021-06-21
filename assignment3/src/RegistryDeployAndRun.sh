echo "Transfering data to the registry node."
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'mkdir -p test/Airlift'
sshpass -f password scp dirRegistry.zip sd101@l040101-ws09.ua.pt:test/Airlift
echo "Decompressing data sent to the registry node."
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'cd test/Airlift ; unzip -uq dirRegistry.zip'
echo "Executing program at the registry node."
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'cd test/Airlift/dirRegistry ; ./registry_com_d.sh sd101'
