#!/usr/bin/env bash

xterm  -T "General Repository" -hold -e "./GeneralRepositoryDeployAndRun.sh" &
xterm  -T "Departure Airport" -hold -e "./DepartureAirportDeployAndRun.sh" &
xterm  -T "Plane" -hold -e "./PlaneDeployAndRun.sh" &
xterm  -T "Destination Airport" -hold -e "./DestinationAirportDeployAndRun.sh" &
sleep 1
xterm  -T "Pilot" -hold -e "./PilotDeployAndRun.sh" &
xterm  -T "Hostess" -hold -e "./HostessDeployAndRun.sh" &
xterm  -T "Passenger" -hold -e "./PassengerDeployAndRun.sh" &
