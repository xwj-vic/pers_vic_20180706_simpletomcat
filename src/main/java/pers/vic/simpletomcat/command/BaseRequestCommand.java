package pers.vic.simpletomcat.command;

import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.util.Map;

/**
 * Create By Vic Xu on 7/9/2018
 */
public abstract class BaseRequestCommand {
    CommandReceiver commandReceiver;

    public Map<String, String> map;

    public BaseRequestCommand(CommandReceiver commandReceiver, Map<String, String> map) {
        this.commandReceiver = commandReceiver;
        this.map = map;
    }

    public CommandReceiver getCommandReceiver() {
        return commandReceiver;
    }

    public void setCommandReceiver(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
