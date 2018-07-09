package pers.vic.simpletomcat.command;

import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.util.Map;

/**
 * Create By Vic Xu on 7/9/2018
 */
public abstract class BaseRequestCommand {
    CommandReceiver commandReceiver;

    public Map<String, String> map;

    BaseRequestCommand(CommandReceiver commandReceiver, Map<String, String> map) {
        this.commandReceiver = commandReceiver;
        this.map = map;
    }

}
