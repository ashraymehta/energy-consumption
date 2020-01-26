# Energy Consumption

[![Build Status](https://travis-ci.org/ashraymehta/energy-consumption.svg?branch=master)](https://travis-ci.org/ashraymehta/energy-consumption)

Energy Consumption is a Java application to collect data about energy consumption from different villages.

## Build

Use the following command to build the project:

```bash
./gradlew clean build
```

#### Note

This project uses [MongoDB v4.2.2](https://www.mongodb.com/download-center/community) for persistent storage. For a successful build, a local MongoDB v4.2.2 installation would be required. 

## Run

Use the following command to start the application:

```bash
./gradlew clean bootRun
```

### Stub Server
This project depends on another application for some of it's functionality. If you're running the application standalone, then please use the stub configuration included with this project.

To use the stub-server, please install [mountebank](http://www.mbtest.org/docs/gettingStarted). After that, you can use the following command from the project working directory to start the stub-server.
```bash
mb --configfile mb-config.json
```

The stub server boots up on the port `12001`. It contains data related to counter ids `1, 2, 3,` and `4`.

## License
[MIT](https://choosealicense.com/licenses/mit/)