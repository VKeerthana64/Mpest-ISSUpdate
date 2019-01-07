package com.retropicker.callback;

import android.graphics.Bitmap;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public interface CallbackPicker {

    void onSuccess(Bitmap bitmap, String ImagePath);

    void onFailure(Throwable error);
}
