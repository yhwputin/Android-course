package com.bytedance.android.lesson.restapi.solution.bean;

/**
 * @author Xavier.S
 * @date 2019.01.18 17:53
 */
public class PostVideoResponse {


    private Object result;
    private String url;
    private boolean success;

    public Object getresult(){
        return result;
    }
    public String geturl(){
        return url;
    }
    public boolean getsuccess(){
        return success;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    // TODO-C2 (3) Implement your PostVideoResponse Bean here according to the response json
}
