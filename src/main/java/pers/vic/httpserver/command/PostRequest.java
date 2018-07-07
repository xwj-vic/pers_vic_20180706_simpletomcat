package pers.vic.httpserver.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.httpserver.HttpCommandReceiver;
import pers.vic.httpserver.entity.BooksEntity;

/**
 * Create by Vic Xu on 2018/7/6
 *
 * @author Administrator
 */
@Data
@AllArgsConstructor
public class PostRequest implements HttpCommand {

    private HttpCommandReceiver commandReceiver;

    private BooksEntity booksEntity;

    @Override
    public String Execute() {
        return commandReceiver.exectPut(booksEntity);
    }
}
