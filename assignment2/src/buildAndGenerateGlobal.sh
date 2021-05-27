echo "Compiling source code."
javac ClientSide/*/*.java ServerSide/*/*.java commonInfrastructures/*.java

echo "Distributing intermediate code to the different execution environments."

echo "  General Repository of Information"
rm -rf generalRepositoryDir
mkdir -p generalRepositoryDir generalRepositoryDir/ServerSide generalRepositoryDir/ServerSide/main generalRepositoryDir/ServerSide/sharedRegions \
         generalRepositoryDir/ClientSide generalRepositoryDir/ClientSide/entities generalRepositoryDir/ClientSide/main generalRepositoryDir/ClientSide/stub generalRepositoryDir/commonInfrastructures        
cp ServerSide/main/SimulPar.class ServerSide/main/GeneralRepositoryMain.class generalRepositoryDir/ServerSide/main
cp ServerSide/entities/GeneralRepositoryProxy.class generalRepositoryDir/ServerSide/entities
cp ServerSide/sharedRegions/GeneralRepositoryInterface.class ServerSide/sharedRegions/GeneralRepository.class generalRepositoryDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class generalRepositoryDir/ClientSide/entities
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ServerCom.class generalRepositoryDir/commonInfrastructures

echo "  Departure Airport"
rm -f departureairportDir
mkdir -p departureairportDir departureairportDir/ServerSide departureairportDir/ServerSide/main departureairportDir/ServerSide/sharedRegions \
         departureairportDir/ClientSide departureairportDir/ClientSide/entities departureairportDir/ClientSide/main departureairportDir/ClientSide/stub departureairportDir/commonInfrastructures        
cp ServerSide/main/SimulPar.class ServerSide/main/DepartureAirportMain.class departureairportDir/ServerSide/main
cp ServerSide/entities/DepartureAirportProxy.class departureairportDir/ServerSide/entities
cp ServerSide/sharedRegions/DepartureAirportInterface.class ServerSide/sharedRegions/DepartureAirport.class ServerSide/sharedRegions/GeneralRepositoryInterface.class departureairportDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class departureairportDir/ClientSide/entities
cp ClientSide/stub GeneralRepositoryStub.class departureairportDir/ClientSide/stub
cp commonInfrastructures/*.class departureairportDir/commonInfrastructures

echo "   Plane"
rm -f planeDir
mkdir -p planeDir planeDir/ServerSide planeDir/ServerSide/main planeDir/ServerSide/sharedRegions \
         planeDir/ClientSide planeDir/ClientSide/entities planeDir/ClientSide/main planeDir/ClientSide/stub planeDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/PlaneMain.class planeDir/ServerSide/main
cp ServerSide/entities/PlaneProxy.class planeDir/ServerSide/entities
cp ServerSide/sharedRegions/PlaneInterface.class ServerSide/sharedRegions/Plane.class ServerSide/sharedRegions/GeneralRepositoryInterface.class planeDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class planeDir/ClientSide/entities
cp ClientSide/stub GeneralRepositoryStub.class planeDir/ClientSide/stub
cp commonInfrastructures/*.class planeDir/commonInfrastructures

echo "   Destination Airport"
rm -f destinationAirportDir
mkdir -p destinationAirportDir destinationAirportDir/ServerSide destinationAirportDir/ServerSide/main destinationAirportDir/ServerSide/sharedRegions \
         destinationAirportDir/ClientSide destinationAirportDir/ClientSide/entities destinationAirportDir/ClientSide/main destinationAirportDir/ClientSide/stub destinationAirportDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/DestinationAirportMain.class destinationAirportDir/ServerSide/main
cp ServerSide/entities/DestinationAirportProxy.class destinationAirportDir/ServerSide/entities
cp ServerSide/sharedRegions/DestinationAirportInterface.class ServerSide/sharedRegions/DestinationAirport.class ServerSide/sharedRegions/GeneralRepositoryInterface.class destinationAirportDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class destinationAirportDir/ClientSide/entities
cp ClientSide/stub GeneralRepositoryStub.class destinationAirportDir/ClientSide/stub
cp commonInfrastructures/*.class destinationAirportDir/commonInfrastructures

echo "   Pilot"
rm -f pilotDir
mkdir -p pilotDir pilotDir/ServerSide pilotDir/ServerSide/main pilotDir/ServerSide/sharedRegions \
         pilotDir/ClientSide pilotDir/ClientSide/entities pilotDir/ClientSide/main pilotDir/ClientSide/stub pilotDir/commonInfrastructures
cp ServerSide/main/SimulPar.class pilotDir/ServerSide/main
cp ClientSide/main/PilotMain.class pilotDir/ClientSide/main
cp ClientSide/entities/Pilot.class ClientSide/entities/PilotStates.class pilotDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class ClientSide/stub/DepartureAirportStub.class ClientSide/stub/DestinationAirportStub.class ClientSide/stub/PlaneStub.class pilotDir/ClientSide/stub
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ClientCom.class pilotDir/commonInfrastructures

echo "   Passenger"
rm -f passengerDir
mkdir -p passengerDir passengerDir/ServerSide passengerDir/ServerSide/main passengerDir/ServerSide/sharedRegions \
         passengerDir/ClientSide passengerDir/ClientSide/entities passengerDir/ClientSide/main passengerDir/ClientSide/stub passengerDir/commonInfrastructures
cp ServerSide/main/SimulPar.class passengerDir/ServerSide/main
cp ClientSide/main/PassengerMain.class passengerDir/ClientSide/main
cp ClientSide/entities/Passenger.class ClientSide/entities/PassengerStates.class passengerDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class ClientSide/stub/DepartureAirportStub.class ClientSide/stub/DestinationAirportStub.class ClientSide/stub/PlaneStub.class passengerDir/ClientSide/stub
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ClientCom.class passengerDir/commonInfrastructures

echo "   Hostess"
rm -f hostessDir
mkdir -p hostessDir hostessDir/ServerSide hostessDir/ServerSide/main hostessDir/ServerSide/sharedRegions \
         hostessDir/ClientSide hostessDir/ClientSide/entities hostessDir/ClientSide/main hostessDir/ClientSide/stub hostessDir/commonInfrastructures
cp ServerSide/main/SimulPar.class hostessDir/ServerSide/main
cp ClientSide/main/HostessMain.class hostessDir/ClientSide/main
cp ClientSide/entities/Hostess.class ClientSide/entities/HostessStates.class hostessDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class ClientSide/stub/DepartureAirportStub.class ClientSide/stub/PlaneStub.class hostessDir/ClientSide/stub
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ClientCom.class hostessDir/commonInfrastructures

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
