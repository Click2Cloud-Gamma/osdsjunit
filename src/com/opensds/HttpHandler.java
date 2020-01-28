package com.opensds;

import com.google.gson.Gson;
import com.opensds.jsonmodels.authtokensrequests.Project;
import com.opensds.jsonmodels.authtokensrequests.Scope;
import com.opensds.jsonmodels.authtokensrequests.Token;
import com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder;
import com.opensds.jsonmodels.inputs.addbackend.AddBackendInputHolder;
import com.opensds.jsonmodels.inputs.createbucket.CreateBucketFileInput;
import com.opensds.jsonmodels.projectsresponses.ProjectsHolder;
import com.opensds.jsonmodels.logintokensrequests.*;
import com.opensds.jsonmodels.responses.listbackends.ListBackendResponse;
import com.opensds.jsonmodels.tokensresponses.TokenHolder;
import com.opensds.jsonmodels.typesresponse.TypesHolder;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class HttpHandler {

    private OkHttpClient client = new OkHttpClient();

    public TokenHolder loginAndGetToken() {
        TokenHolder tokenHolder = null;

        try {
            MediaType mediaType = MediaType.parse("application/json");
            Auth auth = new Auth();

            auth.setIdentity(new Identity());
            auth.getIdentity().getMethods().add("password");

            auth.getIdentity().setPassword(new Password());
            auth.getIdentity().getPassword().setUser(new User());
            auth.getIdentity().getPassword().getUser().setName("admin");
            auth.getIdentity().getPassword().getUser().setPassword("opensds@123");

            auth.getIdentity().getPassword().getUser().setDomain(new Domain());
            auth.getIdentity().getPassword().getUser().getDomain().setName("Default");

            AuthHolder authHolder = new AuthHolder();
            authHolder.setAuth(auth);

            Gson gson = new Gson();
            RequestBody body = RequestBody.create(
                    gson.toJson(authHolder),
                    MediaType.parse("application/json; charset=utf-8")
            );
            Request request = new Request.Builder()
                    .url("http://" + System.getenv("HOST_IP") + ":8088/v3/auth/tokens")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "PostmanRuntime/7.20.1")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Postman-Token", "d1461223-255f-4c72-a3bf-b7410ca5c387,e78d906f-6ffc-4cd0-a51b-3237edf146fa")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Content-Length", "274")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Connection", "keep-alive")
                    .build();


            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            tokenHolder = gson.fromJson(responseBody, TokenHolder.class);
            tokenHolder.setResponseHeaderSubjectToken(response.header("X-Subject-Token"));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenHolder;
    }


    public ProjectsHolder getProjects(String x_auth_token, String userId) {

        ProjectsHolder linksHolder = null;
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                    .readTimeout(5, TimeUnit.MINUTES); // read timeout


            MediaType mediaType = MediaType.parse("application/json");

            Gson gson = new Gson();

            String url = "http://" + System.getenv("HOST_IP") + ":8088/v3/users/<userid>/projects";
            url = url.replaceAll("<userid>", userId);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                    .build();

            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            linksHolder = gson.fromJson(responseBody, ProjectsHolder.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linksHolder;
    }


    public AuthTokenHolder getAuthToken(String x_auth_token) {
        com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder tokenHolder = null;
        try {
            MediaType mediaType = MediaType.parse("application/json");

            com.opensds.jsonmodels.authtokensrequests.Auth auth = new com.opensds.jsonmodels.authtokensrequests.Auth();
            auth.setIdentity(new com.opensds.jsonmodels.authtokensrequests.Identity());
            auth.getIdentity().getMethods().add("token");
            auth.getIdentity().setToken(new Token(x_auth_token));

            auth.setScope(new Scope());
            auth.getScope().setProject(new Project());
            auth.getScope().getProject().setName("admin");
            auth.getScope().getProject().setDomain(new com.opensds.jsonmodels.authtokensrequests.Domain());
            auth.getScope().getProject().getDomain().setId("default");

            com.opensds.jsonmodels.authtokensrequests.AuthHolder authHolder = new com.opensds.jsonmodels.authtokensrequests.AuthHolder();
            authHolder.setAuth(auth);

            Gson gson = new Gson();
            RequestBody body = RequestBody.create(
                    gson.toJson(authHolder),
                    MediaType.parse("application/json; charset=utf-8")
            );
            Request request = new Request.Builder()
                    .url("http://" + System.getenv("HOST_IP") + ":8088/v3/auth/tokens")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "PostmanRuntime/7.20.1")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Postman-Token", "d1461223-255f-4c72-a3bf-b7410ca5c387,e78d906f-6ffc-4cd0-a51b-3237edf146fa")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Content-Length", "274")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .build();


            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            tokenHolder = new com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder();
            tokenHolder = gson.fromJson(responseBody, com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder.class);
            tokenHolder.setResponseHeaderSubjectToken(response.header("X-Subject-Token"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenHolder;
    }


    public TypesHolder getTypes(String x_auth_token, String projId) {
        TypesHolder typesHolder = null;
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                    .readTimeout(5, TimeUnit.MINUTES); // read timeout


            MediaType mediaType = MediaType.parse("application/json");

            Gson gson = new Gson();

            String url = "http://" + System.getenv("HOST_IP") + ":8088/v1/<projectid>/types";

            url = url.replaceAll("<projectid>", projId);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                    .build();

            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            typesHolder = gson.fromJson(responseBody, TypesHolder.class);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return typesHolder;
    }


    public int addBackend(String x_auth_token, String projId, AddBackendInputHolder inputHolder) {

        int code = -1;
        try {
            MediaType mediaType = MediaType.parse("application/json");

            Gson gson = new Gson();
            RequestBody body = RequestBody.create(
                    gson.toJson(inputHolder),
                    MediaType.parse("application/json; charset=utf-8")
            );

            String url = "http://" + System.getenv("HOST_IP") + ":8088/v1/<projectid>/backends";
            url = url.replaceAll("<projectid>", projId);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "PostmanRuntime/7.20.1")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Postman-Token", "d1461223-255f-4c72-a3bf-b7410ca5c387,e78d906f-6ffc-4cd0-a51b-3237edf146fa")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Content-Length", "274")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .build();


            Response response = client.newCall(request).execute();
            code = response.code();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public Response getBackends(String x_auth_token, String projId) {

        Response response = null;

        ListBackendResponse lbr = new ListBackendResponse();
        try {
            MediaType mediaType = MediaType.parse("application/json");

            String url = "http://" + System.getenv("HOST_IP") + ":8088/v1/<projectid>/backends";
            url = url.replaceAll("<projectid>", projId);

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "PostmanRuntime/7.20.1")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .build();


            response = client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    public Response deleteBackend(String x_auth_token, String projId, String backendId) {

        Response response = null;

        ListBackendResponse lbr = new ListBackendResponse();
        try {
            MediaType mediaType = MediaType.parse("application/json");

            String url = "http://" + System.getenv("HOST_IP") + ":8088/v1/<projectid>/backends/" + backendId;
            url = url.replaceAll("<projectid>", projId);

            Request request = new Request.Builder()
                    .url(url)
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "PostmanRuntime/7.20.1")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .build();


            response = client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    public int createBucket(String x_auth_token, CreateBucketFileInput input, String bucketName) {

        int code = -1;
        try {
            MediaType mediaType = MediaType.parse("application/json");

            Gson gson = new Gson();
            RequestBody body = RequestBody.create(
                    input.getXmlPayload(),
                    MediaType.parse("application/xml; charset=utf-8")
            );
            // http://localhost:8088/v1/s3/b123
            String url = "http://" + System.getenv("HOST_IP") + ":8088/v1/s3/" + bucketName;

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/xml")
                    .addHeader("User-Agent", "PostmanRuntime/7.20.1")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Postman-Token", "d1461223-255f-4c72-a3bf-b7410ca5c387,e78d906f-6ffc-4cd0-a51b-3237edf146fa")
                    .addHeader("Host", System.getenv("HOST_IP") + ":8088")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Content-Length", "204")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("X-Auth-Token", x_auth_token)
                    .build();

            Response response = client.newCall(request).execute();
            code = response.code();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
