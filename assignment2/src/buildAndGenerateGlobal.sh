#!/usr/bin/env bash

echo "Compiling source code."
javac ClientSide/*/*.java ServerSide/*/*.java commonInfrastructures/*.java

echo "Distributing intermediate code to the different execution environments."
mkdir -p test/AirLift
echo "  General Repository of Information"
rm -rf test/AirLift/generalRepositoryDir
mkdir -p test/AirLift/generalRepositoryDir test/AirLift/generalRepositoryDir/ServerSide test/AirLift/generalRepositoryDir/ServerSide/main test/AirLift/generalRepositoryDir/ServerSide/sharedRegions test/AirLift/generalRepositoryDir/ServerSide/entities \
         test/AirLift/generalRepositoryDir/ClientSide test/AirLift/generalRepositoryDir/ClientSide/entities test/AirLift/generalRepositoryDir/ClientSide/main test/AirLift/generalRepositoryDir/ClientSide/stub test/AirLift/generalRepositoryDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/GeneralRepositoryMain.class test/AirLift/generalRepositoryDir/ServerSide/main
cp ServerSide/entities/GeneralRepositoryProxy.class test/AirLift/generalRepositoryDir/ServerSide/entities
cp ServerSide/sharedRegions/GeneralRepositoryInterface.class ServerSide/sharedRegions/GeneralRepositoryInterface\$1.class ServerSide/sharedRegions/GeneralRepository.class ServerSide/sharedRegions/GeneralRepository\$1.class test/AirLift/generalRepositoryDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class test/AirLift/generalRepositoryDir/ClientSide/entities
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ServerCom.class test/AirLift/generalRepositoryDir/commonInfrastructures

echo "  Departure Airport"
rm -rf test/AirLift/departureairportDir
mkdir -p test/AirLift/departureairportDir test/AirLift/departureairportDir/ServerSide test/AirLift/departureairportDir/ServerSide/main test/AirLift/departureairportDir/ServerSide/sharedRegions test/AirLift/departureairportDir/ServerSide/entities\
         test/AirLift/departureairportDir/ClientSide test/AirLift/departureairportDir/ClientSide/entities test/AirLift/departureairportDir/ClientSide/main test/AirLift/departureairportDir/ClientSide/stub test/AirLift/departureairportDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/DepartureAirportMain.class test/AirLift/departureairportDir/ServerSide/main
cp ServerSide/entities/DepartureAirportProxy.class test/AirLift/departureairportDir/ServerSide/entities
cp ServerSide/sharedRegions/DepartureAirportInterface.class ServerSide/sharedRegions/DepartureAirportInterface\$1.class ServerSide/sharedRegions/DepartureAirport.class ServerSide/sharedRegions/GeneralRepositoryInterface.class ServerSide/sharedRegions/GeneralRepositoryInterface\$1.class test/AirLift/departureairportDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class\
   ClientSide/entities/Hostess.class ClientSide/entities/Passenger.class ClientSide/entities/Pilot.class\
   ClientSide/entities/Hostess\$1.class ClientSide/entities/Passenger\$1.class ClientSide/entities/Pilot\$1.class test/AirLift/departureairportDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class test/AirLift/departureairportDir/ClientSide/stub
cp commonInfrastructures/*.class test/AirLift/departureairportDir/commonInfrastructures

echo "   Plane"
rm -rf test/AirLift/planeDir
mkdir -p test/AirLift/planeDir test/AirLift/planeDir/ServerSide test/AirLift/planeDir/ServerSide/main test/AirLift/planeDir/ServerSide/sharedRegions test/AirLift/planeDir/ServerSide/entities\
         test/AirLift/planeDir/ClientSide test/AirLift/planeDir/ClientSide/entities test/AirLift/planeDir/ClientSide/main test/AirLift/planeDir/ClientSide/stub test/AirLift/planeDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/PlaneMain.class test/AirLift/planeDir/ServerSide/main
cp ServerSide/entities/PlaneProxy.class test/AirLift/planeDir/ServerSide/entities
cp ServerSide/sharedRegions/PlaneInterface.class ServerSide/sharedRegions/PlaneInterface\$1.class\
        ServerSide/sharedRegions/Plane.class ServerSide/sharedRegions/GeneralRepositoryInterface.class\
        ServerSide/sharedRegions/GeneralRepositoryInterface\$1.class test/AirLift/planeDir/ServerSide/sharedRegions
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class\
   ClientSide/entities/Hostess.class ClientSide/entities/Passenger.class ClientSide/entities/Pilot.class\
   ClientSide/entities/Hostess\$1.class ClientSide/entities/Passenger\$1.class ClientSide/entities/Pilot\$1.class test/AirLift/planeDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class test/AirLift/planeDir/ClientSide/stub
cp commonInfrastructures/*.class test/AirLift/planeDir/commonInfrastructures

echo "   Destination Airport"
rm -rf test/AirLift/destinationAirportDir
mkdir -p test/AirLift/destinationAirportDir test/AirLift/destinationAirportDir/ServerSide test/AirLift/destinationAirportDir/ServerSide/main test/AirLift/destinationAirportDir/ServerSide/sharedRegions test/AirLift/destinationAirportDir/ServerSide/entities \
         test/AirLift/destinationAirportDir/ClientSide test/AirLift/destinationAirportDir/ClientSide/entities test/AirLift/destinationAirportDir/ClientSide/main test/AirLift/destinationAirportDir/ClientSide/stub test/AirLift/destinationAirportDir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/DestinationAirportMain.class test/AirLift/destinationAirportDir/ServerSide/main
cp ServerSide/entities/DestinationAirportProxy.class test/AirLift/destinationAirportDir/ServerSide/entities
cp ServerSide/sharedRegions/DestinationAirportInterface.class ServerSide/sharedRegions/DestinationAirportInterface\$1.class\
   ServerSide/sharedRegions/DestinationAirport.class ServerSide/sharedRegions/GeneralRepositoryInterface.class\
   ServerSide/sharedRegions/GeneralRepositoryInterface\$1.class test/AirLift/destinationAirportDir/ServerSide/sharedRegions
cp ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class ClientSide/entities/Passenger.class ClientSide/entities/Pilot.class\
   ClientSide/entities/Passenger\$1.class ClientSide/entities/Pilot\$1.class test/AirLift/destinationAirportDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class test/AirLift/destinationAirportDir/ClientSide/stub
cp commonInfrastructures/*.class test/AirLift/destinationAirportDir/commonInfrastructures

echo "   Pilot"
rm -rf test/AirLift/pilotDir
mkdir -p test/AirLift/pilotDir test/AirLift/pilotDir/ClientSide test/AirLift/pilotDir/ClientSide/entities\
          test/AirLift/pilotDir/ClientSide/main test/AirLift/pilotDir/ClientSide/stub test/AirLift/pilotDir/commonInfrastructures
cp ClientSide/main/SimulPar.class test/AirLift/pilotDir/ClientSide/main
cp ClientSide/main/PilotMain.class test/AirLift/pilotDir/ClientSide/main
cp ClientSide/entities/Pilot.class ClientSide/entities/Pilot\$1.class\
   ClientSide/entities/PilotStates.class test/AirLift/pilotDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class ClientSide/stub/DepartureAirportStub.class ClientSide/stub/DestinationAirportStub.class ClientSide/stub/PlaneStub.class test/AirLift/pilotDir/ClientSide/stub
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ClientCom.class test/AirLift/pilotDir/commonInfrastructures

echo "   Hostess"
rm -rf test/AirLift/hostessDir
mkdir -p test/AirLift/hostessDir test/AirLift/hostessDir/ClientSide test/AirLift/hostessDir/ClientSide/entities test/AirLift/hostessDir/ClientSide/main test/AirLift/hostessDir/ClientSide/stub test/AirLift/hostessDir/commonInfrastructures
cp ClientSide/main/SimulPar.class test/AirLift/hostessDir/ClientSide/main
cp ClientSide/main/HostessMain.class test/AirLift/hostessDir/ClientSide/main
cp ClientSide/entities/Hostess.class ClientSide/entities/Hostess\$1.class\
    ClientSide/entities/HostessStates.class test/AirLift/hostessDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class ClientSide/stub/DepartureAirportStub.class ClientSide/stub/PlaneStub.class test/AirLift/hostessDir/ClientSide/stub
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ClientCom.class test/AirLift/hostessDir/commonInfrastructures

echo "   Passenger"
rm -rf test/AirLift/passengerDir
mkdir -p test/AirLift/passengerDir test/AirLift/passengerDir/ClientSide test/AirLift/passengerDir/ClientSide/entities\
          test/AirLift/passengerDir/ClientSide/main test/AirLift/passengerDir/ClientSide/stub test/AirLift/passengerDir/commonInfrastructures
cp ClientSide/main/SimulPar.class test/AirLift/passengerDir/ClientSide/main
cp ClientSide/main/PassengerMain.class test/AirLift/passengerDir/ClientSide/main
cp ClientSide/entities/Passenger.class ClientSide/entities/Passenger\$1.class\
   ClientSide/entities/PassengerStates.class test/AirLift/passengerDir/ClientSide/entities
cp ClientSide/stub/GeneralRepositoryStub.class ClientSide/stub/DepartureAirportStub.class ClientSide/stub/DestinationAirportStub.class ClientSide/stub/PlaneStub.class test/AirLift/passengerDir/ClientSide/stub
cp commonInfrastructures/Message.class commonInfrastructures/MessageType.class commonInfrastructures/MessageException.class commonInfrastructures/ClientCom.class test/AirLift/passengerDir/commonInfrastructures

echo "Compressing execution environments."
cd test/AirLift
mkdir -p test/out
echo "  General Repository of Information"
rm -f ../out/generalRepositoryDir.zip
zip -rq ../out/generalRepositoryDir.zip generalRepositoryDir

echo "  Departure Airport"
rm -f ../out/departureairportDir.zip
zip -rq ../out/departureairportDir.zip departureairportDir

echo "  Destination Airport"
rm -f ../out/destinationAirportDir.zip
zip -rq ../out/destinationAirportDir.zip destinationAirportDir

echo "  Plane"
rm -f ../out/planeDir.zip
zip -rq ../out/planeDir.zip planeDir

echo "  Pilot"
rm -f ../out/pilotDir.zip
zip -rq ../out/pilotDir.zip pilotDir

echo "  Passenger"
rm -f ../out/passengerDir.zip
zip -rq ../out/passengerDir.zip passengerDir

echo "  Hostess"
rm -f ../out/hostessDir.zip
zip -rq ../out/hostessDir.zip hostessDir
