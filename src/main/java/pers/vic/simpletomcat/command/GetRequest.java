package pers.vic.simpletomcat.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.util.Map;

/**
 * Create by Vic Xu on 2018/7/6
 *
 * @author Administrator
 */
@Data
@AllArgsConstructor
public class GetRequest implements HttpCommand {

    private CommandReceiver commandReceiver;

    private Map<String, String> map;

    @Override
    public String Execute() {
        return commandReceiver.execteGet(map);
    }
}
