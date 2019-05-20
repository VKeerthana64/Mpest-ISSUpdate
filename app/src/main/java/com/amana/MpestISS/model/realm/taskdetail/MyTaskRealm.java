
package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MyTaskRealm extends RealmObject{

    private String statusCode;

    private String message;

    private RealmList<ListData1> data = null;

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

    public RealmList<ListData1> getData() {
        return data;
    }

    public void setData(RealmList<ListData1> data) {
        this.data = data;
    }

}
