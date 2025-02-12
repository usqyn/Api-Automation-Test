package org.example.simulator;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.net.URIAuthority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

@SuppressWarnings("ALL")
public class HttpConnector extends BasicClassicHttpRequest {
    private static final Logger logger = LogManager.getLogger(HttpConnector.class);

    private final CloseableHttpClient httpClient;

    // Constructor
    public HttpConnector(String method, String scheme, URIAuthority authority, String path) {
        super(method, scheme, authority, path);
        this.httpClient = HttpClients.createDefault();
    }

    // GET request
    public CloseableHttpResponse get() throws IOException, URISyntaxException {
        HttpGet httpGet = new HttpGet(this.getUri());
        logRequest(httpGet);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        logResponse(closeableHttpResponse);
        return closeableHttpResponse;
    }

    // POST request
    public CloseableHttpResponse post(String requestBody) throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(this.getUri());
        httpPost.setHeader("Content-Type", "application/json");

        // Set the request body
        if (requestBody != null) {
            httpPost.setEntity(new StringEntity(requestBody));
        }

        logRequest(httpPost);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
        logResponse(closeableHttpResponse);
        return closeableHttpResponse;
    }

    // Method to log request details
    private void logRequest(ClassicHttpRequest request) throws URISyntaxException {
        logger.info("Executing {} request: {}", request.getMethod(), request.getUri());
        logger.info("Request Headers:");
        request.headerIterator().forEachRemaining(header ->
                logger.info("{}: {}", header.getName(), header.getValue())
        );

        if (request instanceof HttpPost) {
            HttpPost httpPost = (HttpPost) request;
            try {
                if (httpPost.getEntity() != null) {
                    // Log the request body
                    String body = EntityUtils.toString(httpPost.getEntity());
                    logger.info("Request Body: {}", body);

                    // Reset the entity so it can be used in the request
                    httpPost.setEntity(new StringEntity(body));
                }
            } catch (IOException | ParseException e) {
                logger.error("Failed to log request body", e);
            }
        }
    }

    // Method to log response details
    private void logResponse(CloseableHttpResponse response) {
        logger.info("Response status: {}", response.getCode());
        logger.info("Response Headers:");
        response.headerIterator().forEachRemaining(header ->
                logger.info("{}: {}", header.getName(), header.getValue())
        );

        try {
            if (response.getEntity() != null) {
                String responseBody = EntityUtils.toString(response.getEntity());
                logger.info("Response Body: {}", responseBody);

                // Reset the entity so it can be consumed by the caller
                response.setEntity(new StringEntity(responseBody));
            }
        } catch (IOException | ParseException e) {
            logger.error("Failed to log response body", e);
        }
    }

    // Method to clean up resources
    public void close() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }
}