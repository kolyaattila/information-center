# Information-center

### Develoment enviroment

1. Create a file `.env` with same enviroment variables from `sample.env` file.
2. Run `mvn clear install` to create the jars.
3. Run docker-compose `docker-compose -f docker-compose.dev.yaml up --build -d`.
4. If you want to run service, before runnig go to `Run/Edit Configurations...` swich on tab `EnvFile` and check `Enable EnvFile` and also add your `.env` file in the table. 

### Prod enviroment

1. Create a file `.env` with same enviroment variables from `sample.env` file.
2. Run `mvn clear install` to create the jars.
3. Run docker-compose `docker-compose -f docker-compose.prod.yaml up --build -d`.


###For later
- change the profile from docker-compose
environment:
      - SPRING_PROFILES_ACTIVE=local,local-docker

-
