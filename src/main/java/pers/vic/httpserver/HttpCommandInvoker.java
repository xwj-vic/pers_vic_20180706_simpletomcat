package pers.vic.httpserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.vic.httpserver.command.HttpCommand;

/**
 * Create by Vic Xu on 2018/7/6
 * 调用者
 */
@Data
@AllArgsConstructor
public class HttpCommandInvoker {

    private HttpCommand httpCommand;

    public String action() {
        return httpCommand.Execute();
    }

}
