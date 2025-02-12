package org.example.simulator;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIAuthority;
import org.apache.hc.core5.net.URIBuilder;
import org.example.utils.Config;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AppManager extends HttpConnector {
    URIAuthority authority;
    private static final String DEFAULT_AUTHORITY = "aps1-account-api-beta.tplinkcloud.com"; // Default authority

    public AppManager(String method, String scheme, String path) throws URISyntaxException {
        super(method, scheme, new URIAuthority(DEFAULT_AUTHORITY), path);
        this.authority = new URIAuthority(DEFAULT_AUTHORITY);
        setUri(Config.getBaseUrl(), Config.getPath());
    }

    /**
     * Login method to authenticate the user.
     * @param terminalUUID The terminal unique identifier.
     * @param appType The application type.
     * @param cloudUserName The cloud username.
     * @param cloudPassword The cloud password.
     * @return The HTTP response from the login request.
     * @throws URISyntaxException If there is a URI syntax error.
     * @throws IOException If there is an error during the HTTP request.
     */
    public CloseableHttpResponse Login(Object terminalUUID, Object appType, Object cloudUserName, Object cloudPassword) throws URISyntaxException, IOException {
        JSONObject jsonRequest = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("accountBrand", "TPLINK");
        params.put("locale", "en_US");
        params.put("terminalUUID", terminalUUID);
        params.put("appType", appType);
        params.put("cloudUserName", cloudUserName);
        params.put("cloudPassword", cloudPassword);
        jsonRequest.put("params", params);
        jsonRequest.put("method", "login");

        String jsonBody = jsonRequest.toString();
        CloseableHttpResponse responseBody = this.post(jsonBody);
        return responseBody;
    }

    private void setUri(String baseUrl, String path) throws URISyntaxException {
        // Use URIBuilder to construct the URI with query parameters
        URIBuilder uriBuilder = new URIBuilder(baseUrl + path);
        uriBuilder.addParameter("appName", "NBU.ACCOUNT.DELETE.WEB");
        uriBuilder.addParameter("termID", "NBU.ACCOUNT.DELETE.TERM");
        uriBuilder.addParameter("locale", "en_US");
        uriBuilder.addParameter("brand", "TPLINK");

        URI fullUri = uriBuilder.build();
        super.setUri(fullUri);
    }
}