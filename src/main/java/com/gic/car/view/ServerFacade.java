package com.gic.car.view;

import com.gic.car.behavior.CommandExecutor;
import com.gic.car.behavior.command.CommandFactory;
import com.gic.car.behavior.proxy.FieldManager;
import com.gic.car.domain.CommandParams;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class ServerFacade {

    private static ServerFacade serverFacade;

    private ServerFacade() {

    }

    public static ServerFacade getInstance() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }

    @Setter
    CommandParams commandParams;

    CommandExecutor commandExecutor;
    @Autowired
    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public SystemResponse processUserInput(CommandParams inputCommandParams) {
        this.commandParams = inputCommandParams;
        InputValidator inputValidator = InputValidator.getInstance();
        CommandFactory commandFactory = CommandFactory.getInstance();
        FieldManager fieldManager = FieldManager.getInstance();

        this.commandParams = inputValidator.validate(commandParams);

        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.setFieldManager(fieldManager);
        commandExecutor.setCommandFactory(commandFactory);

        commandParams = commandExecutor.executeCommand(commandParams);

        ViewRenderer viewRenderer = ViewRenderer.getInstance();
        commandParams = viewRenderer.renderView(commandParams);
        return commandParams.getSystemResponse();

    }
}
