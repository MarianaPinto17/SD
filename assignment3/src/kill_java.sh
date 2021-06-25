#! /usr/bin/env bash

for ((i =2; i<9; i++));
do
	echo "Kill ws0${i}"
	sshpass -f password ssh sd101@l040101-ws0${i}.ua.pt 'killall -9 java ; ps -ef | grep sd101'
done

echo "Kill ws09"
sshpass -f password ssh sd101@l040101-ws09.ua.pt 'killall -9 java ; killall -9 rmiregistry ; ps -ef | grep sd101'
