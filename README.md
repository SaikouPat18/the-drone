# Drone Delivery System

## Introduction

The Drone Delivery System is a Java application designed to manage and operate drones for urgent package delivery, specifically medications. The application allows for the registration of drones, loading of medications, checking loaded medications, and checking drone availability and battery status.

## POSTMAN API Documentation
https://documenter.getpostman.com/view/28320767/2sAXjPzpTk#b2bf473c-304a-41e4-93c2-65e78404e393

## Setup Instructions for VS Code

1. **Install VS Code and Extensions**:
   - Ensure you have Visual Studio Code installed. Download it from [here](https://code.visualstudio.com/).
   - Install the necessary extensions:
     - [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
     - [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-spring-boot)

2. **Clone the Repository**:
   - If you haven't already, clone the project repository using the terminal inside VS Code:
     ```bash
     git clone <repository-url>
     cd <repository-directory>
     ```

3. **Open the Project in VS Code**:
   - Open the project folder in VS Code by navigating to `File -> Open Folder...` and selecting the project directory.
   - VS Code should automatically recognize it as a Maven project and prompt you to import the project. If prompted, click "Yes."

4. **Build the Project**:
   - Open the terminal in VS Code (`Ctrl + `` or `View -> Terminal`).
   - Run the following Maven command to compile the code, run the tests, and package the application into a JAR file:
     ```bash
     mvn clean install
     ```

5. **Run the Application**:
   - Start the application with the default configuration by running:
     ```bash
     mvn spring-boot:run
     ```
   - Alternatively, you can use the Spring Boot dashboard in VS Code to run the application. Just open the "Spring Boot Dashboard" from the sidebar, and click the play button next to your application.

6. **Run Tests**:
   - To execute all unit tests and report the results, use:
     ```bash
     mvn test
     ```

7. **Preload Data**:
   - The application includes scripts or mechanisms to preload necessary data into the H2 in-memory database, ensuring the required data is available upon startup.

8. **API Testing**:
   - Test the APIs using the provided Postman collection or cURL commands. The API endpoints cover functionalities for registering drones, loading medications, checking loaded medications, and querying drone information.

## Project Structure

- `src/main/java`: Contains the main application code
- `src/test/java`: Contains unit tests
- `src/main/resources`: Contains configuration files
- `pom.xml`: Maven configuration file

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
