package pers.vic.simpletomcat.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.simpletomcat.HttpCommandReceiver;

/**
 * Create by Vic Xu on 2018/7/6
 */
@Data
@AllArgsConstructor
public class DeleteRequest implements HttpCommand {

    private HttpCommandReceiver commandReceiver;

    private String id;

    @Override
    public String Execute() {
        return commandReceiver.exectRemove(this.id);
    }
}
