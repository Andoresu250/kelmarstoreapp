package com.andoresu.kelmarstore.client;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andoresu.kelmarstore.utils.BaseView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import static com.andoresu.kelmarstore.utils.BaseFragment.BASE_TAG;

public class ObserverResponseWhitError<BaseResponse, ErrorResponse> extends ObserverResponse<BaseResponse>{

    private static final String TAG = BASE_TAG + ObserverResponseWhitError.class.getSimpleName();

    public BaseResponse baseResponse;

    private Class<ErrorResponse> persistentErrorResponse;

    public ObserverResponseWhitError(Class<ErrorResponse> persistentErrorResponse) {
        super();
        this.persistentErrorResponse = persistentErrorResponse;
    }

    public ObserverResponseWhitError(@NonNull BaseView baseView, Class<ErrorResponse> persistentErrorResponse) {
        super(baseView);
        this.persistentErrorResponse = persistentErrorResponse;
    }

    @Override
    public void onNext(BaseResponse baseResponse) {
        super.onNext(baseResponse);
        this.baseResponse = baseResponse;
    }

    public Type getType(){
        return new TypeToken<HashMap<String, ErrorResponse>>(){}.getType();
    }

    public ErrorResponse getError(){
        try {
            Log.i(TAG, "getError: try to get string from error body");
            String json = errorResponse.raw;
            Log.i(TAG, "getError: successful get string from error body");
            Log.i(TAG, "createSetting: errorJson " + json);
            Gson gson = new Gson();
            return gson.fromJson(errorResponse.raw, persistentErrorResponse);
        } catch (Exception e) {
            Log.i(TAG, "getError: failed to get string from error body");
            e.printStackTrace();
            return null;
        }
    }

}
