package com.oauthtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SocialGoogleController {

    /* Cambiar esto: */

    @Value("${spring.social.google.app-id}")
    private String clientId = "654562017313-c2j3vnt9i253slup3f2mtmnoeutj4jdd.apps.googleusercontent.com";

    @Value("{spring.social.google.app-secret}")
    private String secretId = "iP00UFVzF52QSOitDauiRvDS";

    private GoogleConnectionFactory factory = new GoogleConnectionFactory(clientId, secretId);

    @GetMapping(value = "/useApp")
    public String useApp() {

        OAuth2Operations operations = factory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();

        params.setRedirectUri("http://localhost:8082/forwardLogin");
        params.setScope("email profile openid");

        String url = operations.buildAuthenticateUrl(params);

        System.out.println("The URL is: " + url);

        return "redirect:" + url;

    }

    @RequestMapping(value = "/forwardLogin")
    public void producer(@RequestParam("code")
                         String authorizationCode) {

        OAuth2Operations operations = factory.getOAuthOperations();
        AccessGrant accessToken = operations.exchangeForAccess(authorizationCode, "http://localhost:8082/forwardLogin", null);

        Connection<Google> connection = factory.createConnection(accessToken);

        Google google = connection.getApi();

        if (google != null) {
            // Redirect a la URL buena.
        }




    }

}
