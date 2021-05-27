echo "Compiling source code."
javac ClientSide/*/*.java ServerSide/*/*.java commonInfrastructures/*.java

echo "Distributing intermediate code to the different execution environments."

echo "  General Repository of Information"
rm -rf generalRepositoryDir
mkdir -p generalRepositoryDir generalRepositoryDir/ServerSide generalRepositoryDir/ServerSide/main generalRepositoryDir/ServerSide/sharedRegions \
         generalRepositoryDir/ClientSide generalRepositoryDir/ClientSide/entities generalRepositoryDir/ClientSide/main generalRepositoryDir/stub generalRepositoryDir/commonInfrastructures        
cp ServerSide/main/SimulPar.class ServerSide/main/GeneralRepositoryMain.class generalRepositoryDir/ServerSide/main
cp ServerSide/entities/GeneralRepositoryProxy.class generalRepositoryDir/ServerSide/entities
cp ServerSide/sharedRegions/GeneralRepositoryInterface.class ServerSide/sharedRegions/GeneralRepository.class generalRepositoryDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class generalRepositoryDir/ClientSide/entities
cp commonInfrastructure/Message.class commonInfrastructure/MessageType.class commonInfrastructure/MessageException.class commonInfrastructure/ServerCom.class generalRepositoryDir/commonInfrastructures

echo "  Departure Airport"
rm -f departureairportDir
mkdir -p departureAirportDir departureAirportDir/ServerSide departureAirportDir/ServerSide/main departureAirportDir/ServerSide/sharedRegions \
         departureAirportDir/ClientSide departureAirportDir/ClientSide/entities departureAirportDir/ClientSide/main departureAirportDir/stub departureAirportDir/commonInfrastructures        
cp ServerSide/main/SimulPar.class ServerSide/main/DepartureAirportMain.class departureAirportDir/ServerSide/main
cp ServerSide/entities/DepartureAirportProxy.class departureAirportDir/ServerSide/entities
cp ServerSide/sharedRegions/DepartureAirportInterface.class ServerSide/sharedRegions/DepartureAirport.class ServerSide/sharedRegions/GeneralRepositoryInterface.class departureAirportDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class departureAirportDir/ClientSide/entities
cp ClientSide/stub GeneralRepositoryStub.class departureAirportDir/ClientSide/stubs
cp commonInfrastructure/*.class departureAirportDir/commonInfrastructures

echo "   Plane"
rm -f planeDir
mkdir -p planeDir planeDir/ServerSide planeDir/ServerSide/main planeDir/ServerSide/sharedRegions \
         planeDir/ClientSide planeDir/ClientSide/entities planeDir/ClientSide/main planeDir/stub planeDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/PlaneMain.class planeDir/ServerSide/main
cp ServerSide/entities/PlaneProxy.class planeDir/ServerSide/entities
cp ServerSide/sharedRegions/PlaneInterface.class ServerSide/sharedRegions/Plane.class ServerSide/sharedRegions/GeneralRepositoryInterface.class planeDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class planeDir/ClientSide/entities
cp ClientSide/stub GeneralRepositoryStub.class planeDir/ClientSide/stubs
cp commonInfrastructure/*.class planeDir/commonInfrastructures

echo "   Destination Airport"
rm -f destinationAirportDir
mkdir -p destinationAirportDir destinationAirportDir/ServerSide destinationAirportDir/ServerSide/main destinationAirportDir/ServerSide/sharedRegions \
         destinationAirportDir/ClientSide destinationAirportDir/ClientSide/entities destinationAirportDir/ClientSide/main destinationAirportDir/stub destinationAirportDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/DestinationAirportMain.class destinationAirportDir/ServerSide/main
cp ServerSide/entities/DestinationAirportProxy.class destinationAirportDir/ServerSide/entities
cp ServerSide/sharedRegions/DestinationAirportInterface.class ServerSide/sharedRegions/DestinationAirport.class ServerSide/sharedRegions/GeneralRepositoryInterface.class destinationAirportDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class destinationAirportDir/ClientSide/entities
cp ClientSide/stub GeneralRepositoryStub.class destinationAirportDir/ClientSide/stubs
cp commonInfrastructure/*.class destinationAirportDir/commonInfrastructures

echo "   Pilot"
rm -f pilotDir
mkdir -p pilotDir pilotDir/ServerSide pilotDir/ServerSide/main pilotDir/ServerSide/sharedRegions \
         pilotDir/ClientSide pilotDir/ClientSide/entities pilotDir/ClientSide/main pilotDir/stub pilotDir/commonInfrastructures
cp serverSide/main/SimulPar.class pilotDir/serverSide/main
cp clientSide/main/PilotMain.class pilotDir/clientSide/main
cp clientSide/entities/Pilot.class clientSide/entities/PilotStates.class pilotDir/clientSide/entities
cp clientSide/stubs/GeneralRepositoryStub.class clientSide/stubs/DepartureAirportStub.class clientSide/stubs/DestinationAirportStub.class clientSide/stubs/PlaneStub.class pilotDir/clientSide/stubs
cp commonInfrastructure/Message.class commonInfrastructure/MessageType.class commonInfrastructure/MessageException.class commonInfrastructure/ClientCom.class pilotDir/commonInfrastructure

echo "   Passenger"
rm -f passengerDir
mkdir -p passengerDir passengerDir/ServerSide passengerDir/ServerSide/main passengerDir/ServerSide/sharedRegions \
         passengerDir/ClientSide passengerDir/ClientSide/entities passengerDir/ClientSide/main passengerDir/stub passengerDir/commonInfrastructures
cp serverSide/main/SimulPar.class passengerDir/serverSide/main
cp clientSide/main/PassengerMain.class passengerDir/clientSide/main
cp clientSide/entities/Passenger.class clientSide/entities/PassengerStates.class passengerDir/clientSide/entities
cp clientSide/stubs/GeneralRepositoryStub.class clientSide/stubs/DepartureAirportStub.class clientSide/stubs/DestinationAirportStub.class clientSide/stubs/PlaneStub.class passengerDir/clientSide/stubs
cp commonInfrastructure/Message.class commonInfrastructure/MessageType.class commonInfrastructure/MessageException.class commonInfrastructure/ClientCom.class passengerDir/commonInfrastructure

echo "   Hostess"
rm -f hostessDir
mkdir -p hostessDir hostessDir/ServerSide hostessDir/ServerSide/main hostessDir/ServerSide/sharedRegions \
         hostessDir/ClientSide hostessDir/ClientSide/entities hostessDir/ClientSide/main hostessDir/stub hostessDir/commonInfrastructures
cp serverSide/main/SimulPar.class hostessDir/serverSide/main
cp clientSide/main/HostessMain.class hostessDir/clientSide/main
cp clientSide/entities/Hostess.class clientSide/entities/HostessStates.class hostessDir/clientSide/entities
cp clientSide/stubs/GeneralRepositoryStub.class clientSide/stubs/DepartureAirportStub.class clientSide/stubs/PlaneStub.class hostessDir/clientSide/stubs
cp commonInfrastructure/Message.class commonInfrastructure/MessageType.class commonInfrastructure/MessageException.class commonInfrastructure/ClientCom.class hostessDir/commonInfrastructure

echo "Compressing execution environments."
echo "  General Repository of Information"
rm -f generalRepositoryDir.zip
zip -rq generalRepositoryDir.zip generalRepositoryDir

echo "  Departure Airport"
rm -f departureairportDir.zip
zip -rq departureairportDir.zip departureairportDir

echo "  Destination Airport"
rm -f destinationAirportDir.zip
zip -rq destinationAirportDir.zip destinationAirportDir

echo "  Plane"
rm -f planeDir.zip
zip -rq planeDir.zip planeDir

echo "  Pilot"
rm - pilotDir.zip
zip -rq pilotDir.zip pilotDir

echo "  Passenger"
rm - passengerDir.zip
zip -rq passengerDir.zip passengerDir

echo "  Hostess"
rm - hostessDir.zip
zip -rq hostessDir.zip hostessDir
