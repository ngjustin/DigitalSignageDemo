package com.momentum.digitalsignagedemo;

import org.json.JSONObject;

/**
 * Created by Justin on 10/28/2016.
 */

public interface AsyncResult {
    void onResult(JSONObject object);
}
