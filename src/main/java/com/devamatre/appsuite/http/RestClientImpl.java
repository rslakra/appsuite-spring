package com.devamatre.appsuite.http;

import com.devamatre.framework.core.BeanUtils;
import com.devamatre.framework.core.security.OAuth2Keys;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class RestClientImpl implements RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClientImpl.class);
    // Access token expiry is 1 hour only
    private static final long ACCESS_TOKEN_EXPIRY_IN_MINUTES = 60;
    // Async refresh access token before expiration (5 minutes buffer)
    private static final long REFRESH_ACCESS_TOKEN_IN_MINUTES = 55;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String USER_AGENT_HEADER = "RestClient-v1.0.0";
    private String clientId;
    private String clientSecret;
    protected String apiBaseUrl;
    private HttpClient httpClient;
    private LoadingCache<String, String> accessTokenCache;

    static {
        OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     *
     */
    private class AccessTokenCacheLoader extends CacheLoader<String, String> {

        /**
         * @param key
         * @return
         * @throws Exception
         */
        @Override
        public String load(String key) throws Exception {
            return getAccessToken();
        }

        /**
         * Reloads the accessToken before expiry.
         *
         * @param key
         * @param preAccessToken
         * @return
         */
        public ListenableFuture<String> reload(String key, String preAccessToken) {
            ListenableFutureTask<String> accessTokenTask = ListenableFutureTask.create(() -> {
                String accessToken = getAccessToken();
                LOGGER.debug("Reloaded access token={}", accessToken);
                return accessToken;
            });

            Executors.newSingleThreadExecutor().execute(accessTokenTask);
            return accessTokenTask;
        }
    }

    public void init() {
        httpClient = HttpClient.newBuilder().build();
        // A cache for access token, allowing Asynchronously refreshing/reloading before token expiration
        accessTokenCache = CacheBuilder.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(ACCESS_TOKEN_EXPIRY_IN_MINUTES, TimeUnit.MINUTES)
            .refreshAfterWrite(REFRESH_ACCESS_TOKEN_IN_MINUTES, TimeUnit.MINUTES)
            .build(new AccessTokenCacheLoader());
    }


    /**
     * @return
     */
    protected String getAccessToken() {
        String accessToken = BeanUtils.EMPTY_STR;
        try {
            String accessTokenUrl = OAuth2Keys.TOKEN_PATH_COMPONENT.buildUrlPrefixWith(getApiBaseUrl());
            List<NameValuePair> formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair(OAuth2Keys.CLIENT_ID.getKeyName(), getClientId()));
            formParams.add(new BasicNameValuePair(OAuth2Keys.CLIENT_SECRET.getKeyName(), getClientSecret()));
            formParams.add(
                new BasicNameValuePair(OAuth2Keys.GRANT_TYPE.getKeyName(), OAuth2Keys.GRANT_TYPE_VALUE.getKeyName()));
            // send POST request
            HttpRequest postHttpRequest = HttpRequest.newBuilder(URI.create(accessTokenUrl))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString())
                .POST(HttpRequest.BodyPublishers.ofString(formParams.toString()))
                .build();

            LOGGER.info("Executing access token request: {}", postHttpRequest);
            httpClient.send(postHttpRequest, null);
            String payload = null;
            accessToken = null; // extract accessToken from response and assign here.
        } catch (Exception ex) {
            LOGGER.error("Failed to get access token!", ex);
        }

        return accessToken;
    }

    /**
     * @param path
     * @param responseType
     * @param <T>
     * @return
     * @throws RestException
     */
    @Override
    public <T> T getEntity(String path, Class<T> responseType) throws RestException {
        // send get request to apiBaseUrl
        return null;
    }

    /**
     * Creates an entity.
     *
     * @param path
     * @param payload
     * @param responseType
     * @return
     * @throws RestException
     */
    @Override
    public <T> T postEntity(String path, Object payload, Class<T> responseType) throws RestException {
        return null;
    }

    /**
     * Updates an entity.
     *
     * @param path
     * @param payload
     * @param responseType
     * @return
     * @throws RestException
     */
    @Override
    public <T> T putEntity(String path, Object payload, Class<T> responseType) throws RestException {
        return null;
    }

    /**
     * Deletes an entity.
     *
     * @param path
     * @param payload
     * @param responseType
     * @return
     * @throws RestException
     */
    @Override
    public <T> T deleteEntity(String path, Object payload, Class<T> responseType) throws RestException {
        return null;
    }
}
