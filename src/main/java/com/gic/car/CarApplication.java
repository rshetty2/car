package com.gic.car;

import com.gic.car.view.*;
import com.gic.car.domain.CommandParams;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CarApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		SystemResponse systemResponse;

		CommandParams commandParams = CommandParams.builder().commandID(MessageState.START_OVER).build();
		ServerFacade serverFacade = ServerFacade.getInstance();
		systemResponse = serverFacade.processUserInput(commandParams);
        do{
			System.out.print(systemResponse.promptMessage());
			String inputString = scanner.nextLine();
			UserInput userInput = new UserInput(inputString);
			commandParams.setCommandID(systemResponse.nextState());
			commandParams.setUserInput(userInput);
			systemResponse = serverFacade.processUserInput(commandParams);
		} while (!systemResponse.nextState().equals(MessageState.EXIT));

        System.out.println (systemResponse.promptMessage());

	}
}
