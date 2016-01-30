package com.lowwor.tuicool.data.api;

/**
 * Created by lowworker on 2015/9/19.
 */
import android.util.Base64;
import retrofit.RequestInterceptor;

/**
 * Interceptor used to authorize requests.
 */
public class ApiRequestIntercepter implements RequestInterceptor {


    @Override
    public void intercept(RequestFacade requestFacade) {

            final String authorizationValue = encodeCredentialsForBasicAuthorization();
            requestFacade.addHeader("Authorization", authorizationValue);
        requestFacade.addHeader("User-Agent", "android/72/MX5/21/5");


    }

    private String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = "192.168.1.100" + ":" + "tuicool";
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }


}