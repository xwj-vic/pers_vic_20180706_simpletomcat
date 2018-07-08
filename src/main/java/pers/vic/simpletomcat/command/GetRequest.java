package pers.vic.simpletomcat.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.simpletomcat.HttpCommandReceiver;

/**
 * Create by Vic Xu on 2018/7/6
 *
 * @author Administrator
 */
@Data
@AllArgsConstructor
public class GetRequest implements HttpCommand {

    private HttpCommandReceiver commandReceiver;

    private String id;

    @Override
    public String Execute() {
        return commandReceiver.execteGet(id);
    }
}
