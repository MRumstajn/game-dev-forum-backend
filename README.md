# GameDeveloperForum - backend

### About

Backend project for the GameDeveloperForum app. The backend contains a few layers, each of which performs a specific task.

- Data layer
    - Contains repositories used to perform IO operations on the database.

- Service layer
  - Contains business login in the form of query/command services used to fetch or generate data.

- Web or controller layer
  - This layer contains controllers which define endpoints that the client side uses. A controller will typically call
  one or more services to perform an action.

### Starting the app

1. Install Docker
2. Start the database container
   - If you are starting the app for the first time, start the docker-compose.yml by running `docker-compose up` in the project root or by opening the file in Intellij
   and clicking the run icon
    - otherwise you can simply open Docker and start the existing `game-dev-forum-backend` container
3. Start the spring boot app

### Configuring the JWT secret
Set `jwtsecret` JVM variable to a minimum 64 character word. Otherwise a fallback word will be generated every time the application starts.
