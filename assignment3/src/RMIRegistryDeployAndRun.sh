echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'mkdir -p test/Airlift'
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'rm -rf test/Airlift/*'
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -f password scp dirRMIRegistry.zip sd101@l040101-ws09.ua.pt:test/Airlift
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'cd test/Airlift ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'cd test/Airlift/dirRMIRegistry ; cp interfaces/*.class /home/sd101/Public/classes/interfaces ; cp set_rmiregistry_d.sh /home/sd101'
echo "Executing program at the RMIregistry node."
sshpass -f password ssh sd101@l040101-ws09.ua.pt './set_rmiregistry_d.sh sd101 22100'
