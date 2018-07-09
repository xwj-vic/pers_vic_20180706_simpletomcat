package pers.vic.simpletomcat.command;

import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.util.Map;

/**
 * Create by Vic Xu on 2018/7/6
 *
 * @author Administrator
 */
public class PostRequest extends BaseRequestCommand implements HttpCommand {
    public PostRequest(CommandReceiver commandReceiver, Map<String, String> map) {
        super(commandReceiver, map);
    }

    @Override
    public String execute() {
        return commandReceiver.exectPut(map);
    }

}
