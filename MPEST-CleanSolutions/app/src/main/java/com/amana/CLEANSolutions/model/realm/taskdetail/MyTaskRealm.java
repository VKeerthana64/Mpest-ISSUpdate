
package com.amana.CLEANSolutions.model.realm.taskdetail;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MyTaskRealm extends RealmObject{

    private String statusCode;

    private String message;

    private RealmList<Datum> data = null;

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

    public RealmList<Datum> getData() {
        return data;
    }

    public void setData(RealmList<Datum> data) {
        this.data = data;
    }

}
