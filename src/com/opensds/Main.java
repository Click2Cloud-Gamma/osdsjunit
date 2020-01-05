package com.opensds;

import com.opensds.jsonmodels.projectsresponses.ProjectsHolder;
import com.opensds.jsonmodels.tokensresponses.TokenHolder;
import com.opensds.jsonmodels.typesresponse.TypesHolder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // write your code here
        HttpHandler httpHandler = new HttpHandler();

        TokenHolder tokenHolder = httpHandler.loginAndGetToken();


        ProjectsHolder projectsHolder = httpHandler.getProjects(tokenHolder.getResponseHeaderSubjectToken(), tokenHolder.getToken().getUser().getId());


        com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder  authTokenHolder = httpHandler.getAuthToken(tokenHolder.getResponseHeaderSubjectToken());

        TypesHolder typesHolder = httpHandler.getTypes(authTokenHolder.getResponseHeaderSubjectToken(), authTokenHolder.getToken().getProject().getId());


        int code = httpHandler.addBackend(authTokenHolder.getResponseHeaderSubjectToken(), authTokenHolder.getToken().getProject().getId());
        //System.out.println(code);


    }

    /*public static void main(String[] args) {

        try {
            File dir = new File("/root/javaproj/osdsjunit/inputs/addbackend");
            File [] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches("^aws-s3+[a-z_1-9-]*.json");
                }
            });

            for (File xmlfile : files) {
                System.out.println(xmlfile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
