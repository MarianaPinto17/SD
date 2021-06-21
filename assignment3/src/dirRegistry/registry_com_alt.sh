CODEBASE="file:///home/"$1"/test/SleepingBarbers/dirRegistry/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     ServerSide.main.ServerRegisterRemoteObject 22101 localhost 22100
