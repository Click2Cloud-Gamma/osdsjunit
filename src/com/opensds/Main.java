package com.opensds;

import com.opensds.jsonmodels.tokensresponses.TokenHolder;

public class Main {

    public static void main(String[] args) {

        // write your code here
        HttpHandler httpHandler = new HttpHandler();

        TokenHolder tokenHolder = httpHandler.loginAndGetToken();
        System.out.println(tokenHolder);

        httpHandler.getProjects(tokenHolder.getReqHeaderToken(), tokenHolder.getToken().getUser().getId());
    }
}
