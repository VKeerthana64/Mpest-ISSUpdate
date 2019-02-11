
package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MyTaskRealm extends RealmObject{

    private String statusCode;

    private String message;

    private RealmList<ListData> data = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RealmList<ListData> getData() {
        return data;
    }

    public void setData(RealmList<ListData> data) {
        this.data = data;
    }

}
