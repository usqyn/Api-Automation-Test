package account;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.simulator.AppManager;
import org.example.utils.Config;
import org.json.JSONObject;
import org.junit.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testBase.commonTestBase;

import java.io.IOException;
import java.net.URISyntaxException;

public class Demo0001_LoginTest extends commonTestBase {
    private static final Logger logger = LogManager.getLogger(Demo0001_LoginTest.class);
    AppManager appManager;
    private final String terminalUUID = "NBU.ACCOUNT.DELETE.TERM";
    private final String appType = "NBU.ACCOUNT.DELETE.WEB";
    private final String cloudUserName = "";
    private final String cloudPassword = "";

    @Before
    public void setup() throws URISyntaxException {
        String baseUrl = Config.getBaseUrl();
        String path = Config.getPath();

        if (baseUrl == null || path == null || baseUrl.isEmpty() || path.isEmpty()) {
            throw new IllegalArgumentException("Base URL or path is not properly configured");
        }

        appManager = new AppManager("POST", "https", path);
    }

    @After
    public void cleanup() throws IOException {
        if (appManager != null) {
            appManager.close();
        }
    }

    @Test
    public void testLogin() throws URISyntaxException, IOException, ParseException {
        // Execute login request
        CloseableHttpResponse response = appManager.Login(terminalUUID, appType, cloudUserName, cloudPassword);

        // Verify status code
        Assert.assertEquals("Unexpected status code", 200, response.getCode());

        // Verify response body
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        logger.info("Response Body: {}", responseBody);

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(responseBody);

        // Assert specific fields (modify these based on your actual API response)
        Assert.assertEquals("Unexpected error code", 0, jsonResponse.getInt("error_code"));
    }
}