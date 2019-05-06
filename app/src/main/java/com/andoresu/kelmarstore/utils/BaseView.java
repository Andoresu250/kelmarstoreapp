package com.andoresu.kelmarstore.utils;


import com.andoresu.kelmarstore.client.ErrorResponse;

public interface BaseView {

    void showProgressIndicator(boolean active);

    void showGlobalError(ErrorResponse errorResponse);

    void onLogoutFinish();

    void showMessage(String msg);

}
