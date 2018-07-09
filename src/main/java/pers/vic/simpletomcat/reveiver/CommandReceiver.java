package pers.vic.simpletomcat.reveiver;

import java.util.Map;

/**
 * Create By Vic Xu on 7/9/2018
 */
public interface CommandReceiver {
    public String execteGet(Map<String, String> map);

    public String exectPut(Map<String, String> map);

    public String exectRemove(Map<String, String> map);
}
