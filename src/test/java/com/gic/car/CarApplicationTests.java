package com.gic.car;

import com.gic.car.behavior.proxy.FieldManager;
import com.gic.car.domain.*;
import com.gic.car.view.MessageState;
import com.gic.car.view.ServerFacade;
import com.gic.car.view.SystemResponse;
import com.gic.car.view.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



class CarApplicationTests {

	private FieldManager fieldManager;

	private ServerFacade serverFacade;

	@BeforeEach
	public void setUp() {
		fieldManager = FieldManager.getInstance();
		fieldManager.clearFieldValues();
		serverFacade = ServerFacade.getInstance();
	}

	@Test
	public void givenWidthAndHeightForField_WhenValid_ThenCreateTheFieldAndShowMessage() {
		SystemResponse systemResponse = create10x20Field();
		assertEquals(MessageState.INITIAL_CAR_SIMULATION_SELECTION, systemResponse.nextState());
	}

	@Test
	public void givenField_WhenCarAdded_ThenAddCarToField() {
		SystemResponse systemResponse = create10x20Field();
		assertEquals(MessageState.INITIAL_CAR_SIMULATION_SELECTION, systemResponse.nextState());

		CommandParams carCommandParam = CommandParams.builder().
				commandID(MessageState.CAR_COMMAND_CREATION).carName("A").carPositionX(4).carPositionY(5).carDirection(Direction.N).
				userInput(new UserInput("LF")).build();
		SystemResponse systemResponseAfterAddingCar = addCarToField(carCommandParam);

		Field fieldStatePostAddingCars  = fieldManager.getField();

		assertAll(() -> assertNotNull(fieldStatePostAddingCars.getPositionToCarsMap()),
				() -> assertNotNull(fieldStatePostAddingCars.getCarNameToCarMap()),
				() -> assertEquals(1,fieldStatePostAddingCars.getPositionToCarsMap().size()),
				() -> assertEquals(1,fieldStatePostAddingCars.getPositionToCarsMap().size()),
				() -> assertEquals(MessageState.CAR_SIMULATION_SELECTION, systemResponseAfterAddingCar.nextState()));
	}

	@Test
	public void givenCarsAddedToField_WhenSimulated_ThenMoveToLastMenu() {
		SystemResponse systemResponseFromFieldCreation = create10x20Field();
		assertEquals(MessageState.INITIAL_CAR_SIMULATION_SELECTION, systemResponseFromFieldCreation.nextState());

		CommandParams carCommandParam = CommandParams.builder().
				commandID(MessageState.CAR_COMMAND_CREATION).carName("A").carPositionX(4).carPositionY(5).carDirection(Direction.N).
				userInput(new UserInput("LF")).build();
		SystemResponse systemResponseAfterAddingCar = addCarToField(carCommandParam);

		assertEquals(MessageState.CAR_SIMULATION_SELECTION, systemResponseAfterAddingCar.nextState());

		CommandParams commandParams = CommandParams.builder().
				commandID(MessageState.CAR_SIMULATION_SELECTION).carName("A").carPositionX(4).carPositionY(5).carDirection(Direction.N).userInput(new UserInput("2")).build();
		SystemResponse systemResponseAfterRunningSimulation = serverFacade.processUserInput(commandParams);

		assertEquals(MessageState.STARTOVER_EXIT_SELECTION, systemResponseAfterRunningSimulation.nextState());
	}

	@Test
	public void givenCarsAddedToField_WhenSimulated_ThenMoveCarPerCommand() {
		SystemResponse systemResponseFromFieldCreation = create10x20Field();
		assertEquals(MessageState.INITIAL_CAR_SIMULATION_SELECTION, systemResponseFromFieldCreation.nextState());

		CommandParams carCommandParam = CommandParams.builder().
				commandID(MessageState.CAR_COMMAND_CREATION).carName("A").carPositionX(4).carPositionY(5).carDirection(Direction.N).
				userInput(new UserInput("LF")).build();
		SystemResponse systemResponseAfterAddingCar = addCarToField(carCommandParam);

		assertEquals(MessageState.CAR_SIMULATION_SELECTION, systemResponseAfterAddingCar.nextState());

		CommandParams commandParams = CommandParams.builder().
				commandID(MessageState.CAR_SIMULATION_SELECTION).carName("A").carPositionX(4).carPositionY(5).carDirection(Direction.N).userInput(new UserInput("2")).build();
		serverFacade.processUserInput(commandParams);

		Field field = fieldManager.getField();
		Optional<Car> car = field.getCarNameToCarMap().values().stream().findFirst();
		Position pos = car.get().getCurrentPositionOnField();
		Direction direction = car.get().getCurrentDirection();

		assertEquals(3,pos.getX());
		assertEquals(5,pos.getY());
		assertEquals("W",direction.toString());
	}

	@Test
	public void givenThreeCars_WhenTwoMoveToSamePosition_ThenCollisionForTwoOnly() {
		SystemResponse systemResponseFromFieldCreation = create10x20Field();

		CommandParams carACommand = CommandParams.builder().
				commandID(MessageState.CAR_COMMAND_CREATION).carName("A").carPositionX(4).carPositionY(5).carDirection(Direction.N).userInput(new UserInput("LF")).build();
		serverFacade.processUserInput(carACommand);

		CommandParams carBCommand = CommandParams.builder().
				commandID(MessageState.CAR_COMMAND_CREATION).carName("B").carPositionX(2).carPositionY(5).carDirection(Direction.N).userInput(new UserInput("RF")).build();
		serverFacade.processUserInput(carBCommand);

		CommandParams carCCommand = CommandParams.builder().
				commandID(MessageState.CAR_COMMAND_CREATION).carName("C").carPositionX(6).carPositionY(6).carDirection(Direction.S).userInput(new UserInput("FFFR")).build();
		serverFacade.processUserInput(carCCommand);

		CommandParams simulationCommand = CommandParams.builder().
				commandID(MessageState.CAR_SIMULATION_SELECTION).userInput(new UserInput("2")).build();

		serverFacade.processUserInput(simulationCommand);

		Field field = fieldManager.getField();
		Car carA = field.getCarNameToCarMap().get("A");
		Car carB = field.getCarNameToCarMap().get("B");
		Car carC = field.getCarNameToCarMap().get("C");


		assertEquals(3,carA.getCurrentPositionOnField().getX());
		assertEquals(5,carA.getCurrentPositionOnField().getY());
		assertEquals("W",carA.getCurrentDirection().toString());
		assertTrue(carA.isInCollision());

		assertEquals(3,carB.getCurrentPositionOnField().getX());
		assertEquals(5,carB.getCurrentPositionOnField().getY());
		assertEquals("E",carB.getCurrentDirection().toString());
		assertTrue(carB.isInCollision());

		assertEquals(6,carC.getCurrentPositionOnField().getX());
		assertEquals(3,carC.getCurrentPositionOnField().getY());
		assertEquals("W",carC.getCurrentDirection().toString());
		assertFalse(carC.isInCollision());
	}

	private SystemResponse addCarToField(CommandParams commandParams) {
        return serverFacade.processUserInput(commandParams);
	}

	private SystemResponse create10x20Field() {
		CommandParams commandParams = CommandParams.builder().
				commandID(MessageState.WELCOME).build();
		UserInput userInput = new UserInput("10 20");
		commandParams.setUserInput(userInput);
        return serverFacade.processUserInput(commandParams);
	}



}
