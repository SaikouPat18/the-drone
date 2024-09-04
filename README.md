# Drone Delivery System

## Introduction

The Drone Delivery System is a Java application designed to manage and operate drones for urgent package delivery, specifically medications. The application allows for the registration of drones, loading of medications, checking loaded medications, and checking drone availability and battery status.

## POSTMAN API Documentation
https://documenter.getpostman.com/view/28320767/2sAXjPzpTk#b2bf473c-304a-41e4-93c2-65e78404e393

## Build, Run, and Test Instructions

To build, run, and test the project, follow these steps:

1. **Build the Project**: 
   - Open a terminal and navigate to the root directory of the project.
   - Run the following Maven command to compile the code, run the tests, and package the application into a JAR file:
     ```bash
     mvn clean install
     ```

2. **Run the Application**: 
   - Start the application with the default configuration by executing:
     ```bash
     mvn spring-boot:run
     ```

3. **Run Tests**: 
   - To execute all the unit tests and report the results, use:
     ```bash
     mvn test
     ```

4. **Preload Data**: 
   - The application includes scripts or mechanisms to preload necessary data into the H2 in-memory database, ensuring the required data is available upon startup.

5. **API Testing**: 
   - Test the APIs using the provided Postman collection or cURL commands. The API endpoints cover functionalities for registering drones, loading medications, checking loaded medications, and querying drone information.

## Project Structure

- `src/main/java`: Contains the main application code
- `src/test/java`: Contains unit tests
- `src/main/resources`: Contains configuration files
- `pom.xml`: Maven configuration file

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
