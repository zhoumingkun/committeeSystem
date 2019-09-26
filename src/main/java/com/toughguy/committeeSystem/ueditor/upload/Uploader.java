package com.toughguy.committeeSystem.ueditor.upload;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.toughguy.committeeSystem.ueditor.define.State;

public class Uploader {
    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        } else {
            state = new BinaryUploader().save(this.request, this.conf);
        }

        return state;
    }
}
