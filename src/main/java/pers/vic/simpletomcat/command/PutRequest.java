package pers.vic.simpletomcat.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.simpletomcat.HttpCommandReceiver;
import pers.vic.simpletomcat.entity.BooksEntity;

/**
 * Create by Vic Xu on 2018/7/6
 */
@Data
@AllArgsConstructor
public class PutRequest implements HttpCommand {

    private HttpCommandReceiver commandReceiver;

    private BooksEntity booksEntity;

    @Override
    public String Execute() {
        return commandReceiver.exectPut(booksEntity);
    }
}
