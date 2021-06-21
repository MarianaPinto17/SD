#!/usr/bin/env bash

echo "Compiling source code."
javac */*.java */*/*.java

echo "Distributing intermediate code to the different execution environments."
echo "  RMI registry"
rm -rf dirRMIRegistry/interfaces
mkdir -p dirRMIRegistry/interfaces
cp interfaces/*.class dirRMIRegistry/interfaces

echo "  Register Remote Objects"
rm -rf dirRegistry/ServerSide dirRegistry/interfaces
mkdir -p dirRegistry/ServerSide dirRegistry/ServerSide/main dirRegistry/ServerSide/objects dirRegistry/interfaces
cp ServerSide/main/ServerRegisterRemoteObject.class dirRegistry/ServerSide/main
cp ServerSide/objects/RegisterRemoteObject.class dirRegistry/ServerSide/objects
cp interfaces/Register.class dirRegistry/interfaces
echo "  General Repository of Information"
rm -rf dirGeneralRepos/ServerSide dirGeneralRepos/ClientSide dirGeneralRepos/interfaces
mkdir -p dirGeneralRepos/ServerSide dirGeneralRepos/ServerSide/main dirGeneralRepos/ServerSide/objects dirGeneralRepos/interfaces \
         dirGeneralRepos/ClientSide dirGeneralRepos/ClientSide/entities
cp ServerSide/main/SimulPar.class ServerSide/main/GeneralRepositoryMain.class dirGeneralRepos/ServerSide/main
cp ServerSide/objects/GeneralRepository.class dirGeneralRepos/ServerSide/objects
cp interfaces/*.class dirGeneralRepos/interfaces
cp ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class ClientSide/entities/PilotStates.class dirGeneralRepos/ClientSide/entities

echo "  Departure Airport"
rm -rf dirDepAir/ServerSide dirDepAir/ClientSide dirDepAir/interfaces dirDepAir/commonInfrastructures
mkdir -p dirDepAir/ServerSide dirDepAir/ServerSide/main dirDepAir/ServerSide/objects dirDepAir/interfaces \
         dirDepAir/ClientSide dirDepAir/ClientSide/entities dirDepAir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/DepartureAirportMain.class dirDepAir/ServerSide/main
cp ServerSide/objects/DepartureAirport.class dirDepAir/ServerSide/objects
cp interfaces/*.class dirDepAir/interfaces
cp ClientSide/entities/PilotStates.class ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class dirDepAir/ClientSide/entities
cp commonInfrastructures/*.class dirDepAir/commonInfrastructures

echo "  Destination Airport"
rm -rf dirDesAir/ServerSide dirDesAir/ClientSide dirDesAir/interfaces dirDesAir/commonInfrastructures
mkdir -p dirDesAir/ServerSide dirDesAir/ServerSide/main dirDesAir/ServerSide/objects dirDesAir/interfaces \
         dirDesAir/ClientSide dirDesAir/ClientSide/entities dirDesAir/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/DestinationAirportMain.class dirDesAir/ServerSide/main
cp ServerSide/objects/DestinationAirport.class dirDesAir/ServerSide/objects
cp interfaces/*.class dirDesAir/interfaces
cp ClientSide/entities/PilotStates.class ClientSide/entities/PassengerStates.class dirDesAir/ClientSide/entities
cp commonInfrastructures/*.class dirDesAir/commonInfrastructures

echo "  Plane"
rm -rf dirPlane/ServerSide dirPlane/ClientSide dirPlane/interfaces dirPlane/commonInfrastructures
mkdir -p dirPlane/ServerSide dirPlane/ServerSide/main dirPlane/ServerSide/objects dirPlane/interfaces \
         dirPlane/ClientSide dirPlane/ClientSide/entities dirPlane/commonInfrastructures
cp ServerSide/main/SimulPar.class ServerSide/main/PlaneMain.class dirPlane/ServerSide/main
cp ServerSide/objects/Plane.class dirPlane/ServerSide/objects
cp interfaces/*.class dirPlane/interfaces
cp ClientSide/entities/PilotStates.class ClientSide/entities/HostessStates.class ClientSide/entities/PassengerStates.class dirPlane/ClientSide/entities
cp commonInfrastructures/*.class dirPlane/commonInfrastructures



echo "  Pilot"
rm -rf dirPilot/ServerSide dirPilot/ClientSide dirPilot/interfaces
mkdir -p dirPilot/ServerSide dirPilot/ServerSide/main dirPilot/ClientSide dirPilot/ClientSide/main dirPilot/ClientSide/entities \
         dirPilot/interfaces dirPilot/commonInfrastructures
cp ServerSide/main/SimulPar.class dirPilot/ServerSide/main
cp ClientSide/main/PilotMain.class dirPilot/ClientSide/main
cp ClientSide/entities/Pilot.class ClientSide/entities/PilotStates.class dirPilot/ClientSide/entities
cp interfaces/DepartureAirportInterface.class interfaces/DestinationAirportInterface.class interfaces/PlaneInterface.class \
    interfaces/GeneralRepositoryInterface.class interfaces/Message.class interfaces/MessageException.class dirPilot/interfaces
cp commonInfrastructures/*.class dirPlane/commonInfrastructures

echo "  Passengers"
rm -rf dirPassengers/ServerSide dirPassengers/ClientSide dirPassengers/interfaces
mkdir -p dirPassengers/ServerSide dirPassengers/ServerSide/main dirPassengers/ClientSide dirPassengers/ClientSide/main dirPassengers/ClientSide/entities \
         dirPassengers/interfaces dirPassengers/commonInfrastructures
cp ServerSide/main/SimulPar.class dirPassengers/ServerSide/main
cp ClientSide/main/PassengerMain.class dirPassengers/ClientSide/main
cp ClientSide/entities/Passenger.class ClientSide/entities/PassengerStates.class dirPassengers/ClientSide/entities
cp interfaces/DepartureAirportInterface.class interfaces/DestinationAirportInterface.class interfaces/PlaneInterface.class \
    interfaces/GeneralRepositoryInterface.class interfaces/Message.class interfaces/MessageException.class dirPassengers/interfaces
cp commonInfrastructures/*.class dirPlane/commonInfrastructures

echo "  Hostess"
rm -rf dirHostess/ServerSide dirHostess/ClientSide dirHostess/interfaces
mkdir -p dirHostess/ServerSide dirHostess/ServerSide/main dirHostess/ClientSide dirHostess/ClientSide/main dirHostess/ClientSide/entities \
         dirHostess/interfaces dirHostess/commonInfrastructures
cp ServerSide/main/SimulPar.class dirHostess/ServerSide/main
cp ClientSide/main/HostessMain.class dirHostess/ClientSide/main
cp ClientSide/entities/Hostess.class ClientSide/entities/HostessStates.class dirHostess/ClientSide/entities
cp interfaces/DepartureAirportInterface.class interfaces/PlaneInterface.class \
    interfaces/GeneralRepositoryInterface.class interfaces/Message.class interfaces/MessageException.class dirHostess/interfaces
cp commonInfrastructures/*.class dirPlane/commonInfrastructures



echo "Compressing execution environments."
echo "  RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry
echo "  Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry
echo "  General Repository of Information"
rm -f  dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos
echo "  Departure Airport"
rm -f  dirDepAir.zip
zip -rq dirDepAir.zip dirDepAir
echo "  Destination Airport"
rm -f  dirDesAir.zip
zip -rq dirDesAir.zip dirDesAir
echo "  Plane"
rm -f  dirPlane.zip
zip -rq dirPlane.zip dirPlane
echo "  Pilot"
rm -f  dirPilot.zip
zip -rq dirPilot.zip dirPilot
echo "  Passengers"
rm -f  dirPassengers.zip
zip -rq dirPassengers.zip dirPassengers
echo "  Hostess"
rm -f  dirHostess.zip
zip -rq dirHostess.zip dirHostess

