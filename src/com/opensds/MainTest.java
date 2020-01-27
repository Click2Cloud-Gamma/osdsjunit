package com.opensds;

import com.google.gson.Gson;
import com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder;
import com.opensds.jsonmodels.inputs.addbackend.AddBackendInputHolder;
import com.opensds.jsonmodels.projectsresponses.ProjectsHolder;
import com.opensds.jsonmodels.responses.listbackends.Backend;
import com.opensds.jsonmodels.responses.listbackends.ListBackendResponse;
import com.opensds.jsonmodels.tokensresponses.TokenHolder;
import com.opensds.jsonmodels.typesresponse.Type;
import com.opensds.jsonmodels.typesresponse.TypesHolder;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;


import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// how to get POJO from any response JSON, use this site
// http://pojo.sodhanalibrary.com/

@ExtendWith(TestResultHTMLPrinter.class)
class MainTest {

    private static com.opensds.jsonmodels.authtokensresponses.AuthTokenHolder mAuthTokenHolder = null;
    private static TypesHolder mTypesHolder = null;
    private static HttpHandler mHttpHandler = new HttpHandler();

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {

        TokenHolder tokenHolder = getHttpHandler().loginAndGetToken();
        ProjectsHolder projectsHolder = getHttpHandler().getProjects(tokenHolder.getResponseHeaderSubjectToken(),
                tokenHolder.getToken().getUser().getId());
        mAuthTokenHolder = getHttpHandler().getAuthToken(tokenHolder.getResponseHeaderSubjectToken());
        mTypesHolder = getHttpHandler().getTypes(getAuthTokenHolder().getResponseHeaderSubjectToken(),
                getAuthTokenHolder().getToken().getProject().getId());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Get all backend inputs from a folder and create the backend(s) in OpenSDS")
    public void testCreateBackendsForAllTypes() {
        // load input files for each type and create the backend
        for (Type t : getTypesHolder().getTypes()) {
            List<File> listOfIInputsForType =
                    Utils.listFilesMatchingBeginsWithPatternInPath(t.getName(),
                            "/root/javaproj/osdsjunit/inputs/addbackend");
            Gson gson = new Gson();
            // add the backend specified in each file
            for (File file : listOfIInputsForType) {
                String content = Utils.readFileContentsAsString(file);
                assertNotNull(content);

                AddBackendInputHolder inputHolder = gson.fromJson(content, AddBackendInputHolder.class);
                int code = getHttpHandler().addBackend(getAuthTokenHolder().getResponseHeaderSubjectToken(),
                        getAuthTokenHolder().getToken().getProject().getId(),
                        inputHolder);
                assertEquals(code, 200);

                Response lbr = getHttpHandler().getBackends(getAuthTokenHolder().getResponseHeaderSubjectToken(),
                        getAuthTokenHolder().getToken().getProject().getId());
                assertEquals(lbr.code(), 200);
                try {
                    ListBackendResponse listBackendResponse = gson.fromJson(lbr.body().string(), ListBackendResponse.class);
                    boolean found = false;
                    String backendId = null;
                    // check the newly created backend, is listed in the output of listBackends
                    for(Backend b : listBackendResponse.getBackends())
                    {
                        if(b.getName().equals(inputHolder.getName()))
                        {
                            backendId = b.getId();
                            found = true;
                            break;
                        }
                    }
                    assertTrue(found);
                    // now, delete the backend, to restore system to original state
                    Response delbr = getHttpHandler().deleteBackend(getAuthTokenHolder().getResponseHeaderSubjectToken(),
                            getAuthTokenHolder().getToken().getProject().getId(),
                            backendId);
                    assertEquals(delbr.code(), 200);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static AuthTokenHolder getAuthTokenHolder() {
        return mAuthTokenHolder;
    }

    public TypesHolder getTypesHolder() {
        return mTypesHolder;
    }

    public static HttpHandler getHttpHandler() {
        return mHttpHandler;
    }
}