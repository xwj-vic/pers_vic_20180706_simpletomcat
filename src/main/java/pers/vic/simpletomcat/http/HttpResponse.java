package pers.vic.simpletomcat.http;

import org.apache.commons.lang3.StringUtils;
import pers.vic.simpletomcat.command.DeleteRequest;
import pers.vic.simpletomcat.command.GetRequest;
import pers.vic.simpletomcat.command.PostRequest;
import pers.vic.simpletomcat.command.PutRequest;
import pers.vic.simpletomcat.data.ResourceList;
import pers.vic.simpletomcat.invoker.HttpCommandInvoker;
import pers.vic.simpletomcat.reveiver.CommandReceiver;

import java.io.File;
import java.util.Map;

/**
 * Create By Vic Xu on 7/9/2018
 */
public class HttpResponse {

    public void requestHandle(Map<String, String> map, HttpRequest httpRequest, CommandReceiver receiver) {
        try {
            String result = "";
            switch (map.get("request")) {
                case "GET":
                    result = new HttpCommandInvoker(new GetRequest(receiver, map)).action();
                    break;
                case "POST":
                    result = new HttpCommandInvoker(new PostRequest(receiver, map)).action();
                    break;
                case "PUT":
                    result = new HttpCommandInvoker(new PutRequest(receiver, map)).action();
                    break;
                case "DELETE":
                    result = new HttpCommandInvoker(new DeleteRequest(receiver, map)).action();
                    break;
                default:
                    break;
            }
            httpRequest.doRequestUtil(result, map.get("host"));
        } catch (Exception e) {
            httpRequest.doExceptionRequest("500");
            e.printStackTrace();
        }
    }

    public void fileHandle(String path, HttpRequest httpRequest, HttpKeyPointUtil keyPointUtil) {
        path = keyPointUtil.getPath(path);
        String type = keyPointUtil.getContentType(path);
        File file = new File(path);
        if (file.exists()) {
            httpRequest.doRequestFile(file, type);
        } else if (StringUtils.isNotBlank(ResourceList.getRedirectUrl(path))) {
            httpRequest.doRedirct(ResourceList.getRedirectUrl(path));
        } else {
            httpRequest.doExceptionRequest("404");
        }
    }


}
