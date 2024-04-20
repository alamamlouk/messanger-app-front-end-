package com.example.messasingchat.Shared;

import com.android.volley.VolleyError;

public class ErrorHandler {
    public static String getError(VolleyError volleyError){
        String errorMessage;
        switch (volleyError.getClass().getSimpleName()) {
            case "NetworkError":
                errorMessage = "We ran into a NetworkError";
                break;
            case "ServerError":
                errorMessage = "We ran into a ServerError";
                break;
            case "AuthFailureError":
                errorMessage = "User not found";
                break;
            case "ParseError":
                errorMessage = "We ran into a ParseError";
                break;
            case "TimeoutError":
                errorMessage = "We ran into a TimeoutError";
                break;
            case "NoConnectionError":
                errorMessage = "We ran into a NoConnectionError";
                break;
            default:
                errorMessage = "We ran into a problem";
                break;
        }
        return errorMessage;
    }
}
