
# How to

1. Start the docker compose: `docker-compose -f 3-redis-pubsub/docker-compose.yaml up`
2. Start the backend 1: `./gradlew :3-redis-pubsub:bootRun --args='--server.port=8080'`
3. Start the backend 2: `./gradlew :3-redis-pubsub:bootRun --args='--server.port=8081'`
4. Open the browser and go to `http://localhost:8080/` and `http://localhost:8081/` (2 tabs)
5. Open the browser and go to `http://localhost:8080/message-sender.html`
6. Send a message to a user

