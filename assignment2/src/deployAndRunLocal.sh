#!/usr/bin/env bash

xterm  -T "General Repository" -hold -e "cd test/AirLift/generalRepositoryDir ; java ServerSide.main.GeneralRepositoryMain 22100" &
xterm  -T "Departure Airport" -hold -e "cd test/AirLift/departureairportDir ; java ServerSide.main.DepartureAirportMain 22101 localhost 22100" &
xterm  -T "Plane" -hold -e "cd test/AirLift/planeDir ; java ServerSide.main.PlaneMain 22103 localhost 22100" &
xterm  -T "Destination Airport" -hold -e "cd test/AirLift/destinationAirportDir ; java ServerSide.main.DestinationAirportMain 22102 localhost 22100" &
sleep 1
xterm  -T "Pilot" -hold -e "cd test/AirLift/pilotDir ; java ClientSide.main.PilotMain localhost 22101 localhost 22102 localhost 22103 localhost 22100" &
sleep 1
xterm  -T "Hostess" -hold -e "cd test/AirLift/hostessDir ; java ClientSide.main.HostessMain localhost 22101 localhost 22103" &
sleep 1
xterm  -T "Passenger" -hold -e "cd test/AirLift/passengerDir ; java ClientSide.main.PassengerMain localhost 22101 localhost 22102 localhost 22103" &
