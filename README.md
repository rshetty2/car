# Car simulaton app
## Project structure
The project is divided into following structure
 - CarApplication is the main class that starts the command line interface. This is the Springboot Application main class. 
 - Behavior package : Contains the Commands,CommandFactory, FieldManager proxy and CommandExecutor that 
   perform the core business logic
 - Domain package: Models the Car, Field and other state Object 
 - View package : Contains all the View side functionalities such as ServerFacade, UserInput capture, SystemResponse,
   ViewRender 

## Dependencies
All dependecies are listed in pom.xml file of maven. The dependencies listed in pom.xml are:
- Springboot 3.2
- Java 17
- Maven 
- Lambok
- Spring boot test
- Apache Commons 3.14.0


## How to run tests
Navigate to the project root folder and run the below command on terminal (Mac/Linux) or Commandline (Windows)

```shell
mvn test -Dtest="com.gic.car.**"
```

## How to run application
Navigate to the project root folder and run the below command on terminal (Mac/Linux) or Commandline (Windows)

```shell
mvn spring-boot:run
```

## Design Patterns Used
* Builder : CommandParams, that is a view side model, have dynamic construct based on the specific flow and not all input is available for every user action. 
    Therefore, builder is useful. 
    Similarly, Car objects has builder for easy object creation. This also helps in testing.
* Command : All user action is modelled as Command. CommandFactory class acts as the factory to create various commands. 
* State machine : Although this is not a pattern, but through the use of MessageState and CommandParam classes, I have tried to model a state machine 
    to capture the user action events and tie it to the corresponding message.
* Proxy : The FieldManager class acts as a proxy to the Field object. This decouples the Command objects from directly dealing with the core data object - Field, Car
* Aggregate - The CommandParam class is an aggregate of UserInput, SystemResponse and interim user data. 
* Facade - ServerFacade acts as an Entry point for client to use the backend services. It hides the server orchestration details from client. 
* MVC : ViewRender enriches the message displayed to the users.  

## Future Improvements
* Custom Exception class created. It may be used to throw input validation errors and general runtime exception when performing command operations.
* Enhance code coverage and add integration tests.
