#!/bin/bash

#run docker compose file
docker compose up -d

echo "waiting..... to start kafka for a minute"
#sleep for 1 minute to start kafka on docker
sleep 1m

# open conductor ui for kafka
xdg-open http://localhost:8080

#open docker terminal to execute commands
docker exec -it kafka /bin/bash

# exit the local terminl, but we have opened the docker terminal, that will still be open
exit
