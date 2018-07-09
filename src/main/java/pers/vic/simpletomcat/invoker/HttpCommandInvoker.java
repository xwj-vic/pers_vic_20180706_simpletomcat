package pers.vic.simpletomcat.invoker;

import pers.vic.simpletomcat.command.HttpCommand;

/**
 * Create by Vic Xu on 2018/7/6
 * 调用者
 */
public class HttpCommandInvoker {

    private HttpCommand httpCommand;

    public HttpCommandInvoker(HttpCommand httpCommand) {
        this.httpCommand = httpCommand;
    }

    public String action() {
        return httpCommand.execute();
    }

}
