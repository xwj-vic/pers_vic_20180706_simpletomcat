package pers.vic.simpletomcat.entity;

import pers.vic.simpletomcat.reveiver.CommandReceiver;

/**
 * Create by Vic Xu on 2018/7/9
 */
public class ConnectInfo {

    private int port;

    private CommandReceiver receiver;

    public ConnectInfo(int port, CommandReceiver receiver) {
        this.port = port;
        this.receiver = receiver;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public CommandReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(CommandReceiver receiver) {
        this.receiver = receiver;
    }
}
