package com.devamatre.appsuite.spring.client;

import com.devamatre.framework.core.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 10/9/21 4:50 PM
 */
//@Service
public final class ApiRestClient extends RestTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestClient.class);

    private final String apiHostBaseUrl;

    /**
     * @param apiHostBaseUrl
     */
    public ApiRestClient(final String apiHostBaseUrl) {
        LOGGER.debug("ApiRestClient({})", apiHostBaseUrl);
        this.apiHostBaseUrl = apiHostBaseUrl;
    }

    /**
     * @param apiHostBaseUrl
     * @param requestFactory
     */
    public ApiRestClient(final String apiHostBaseUrl, ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        LOGGER.debug("ApiRestClient({}, {})", apiHostBaseUrl, requestFactory);
        this.apiHostBaseUrl = apiHostBaseUrl;
    }

    /**
     * @param apiHostBaseUrl
     * @param messageConverters
     */
    public ApiRestClient(final String apiHostBaseUrl, List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        LOGGER.debug("ApiRestClient({}, {})", apiHostBaseUrl, messageConverters);
        this.apiHostBaseUrl = apiHostBaseUrl;
    }

    /**
     * @return
     */
    public String getApiHostBaseUrl() {
        return this.apiHostBaseUrl;
    }

    /**
     * Returns the URI String build with the <code>pathSegment/code> and <code>uriParams</code>.
     *
     * @param pathSegment
     * @param uriParams
     * @return
     */
    public String toUriString(final String pathSegment, Map<String, ?> uriParams) {
        LOGGER.debug("+toUriString({}, {})", pathSegment, uriParams);
        final UriComponentsBuilder uriBuilder;
        if (BeanUtils.isNotEmpty(pathSegment)) {
            uriBuilder = UriComponentsBuilder.fromHttpUrl(BeanUtils.pathSegments(getApiHostBaseUrl(), pathSegment));
        } else {
            uriBuilder = UriComponentsBuilder.fromHttpUrl(BeanUtils.pathSegments(getApiHostBaseUrl()));
        }

        if (BeanUtils.isNotEmpty(uriParams)) {
            uriParams.forEach((key, value) -> uriBuilder.queryParam(key, "{" + key + "}"));
        }

        String url = uriBuilder.encode().toUriString();
        LOGGER.debug("-toUriString(), url:{}", url);
        return url;
    }

    /**
     * @param pathSegment
     * @return
     */
    public String toUriString(final String pathSegment) {
        return toUriString(pathSegment, null);
    }

    /**
     * (CRUD) Operations (Create, Read, Update, Delete)
     */

    /**
     * @param pathSegment
     * @param request
     * @param responseType
     * @param uriParams
     * @param <T>
     * @return
     * @throws ApiClientException
     */
    @Nullable
    public <T> T doPost(String pathSegment, @Nullable Object request, Class<T> responseType, Object... uriParams)
        throws ApiClientException {
        return super.postForObject(toUriString(pathSegment), request, responseType, uriParams);
    }

    /**
     * @param pathSegment
     * @param request
     * @param responseType
     * @param uriParams
     * @param <T>
     * @return
     * @throws ApiClientException
     */
    @Nullable
    public <T> T doPost(String pathSegment, @Nullable Object request, Class<T> responseType,
                        Map<String, ?> uriParams)
        throws ApiClientException {
        return super.postForObject(toUriString(pathSegment, uriParams), request, responseType, uriParams);
    }

    /**
     * @param pathSegment
     * @param request
     * @param responseType
     * @param <T>
     * @return
     * @throws ApiClientException
     */
    @Nullable
    public <T> T doPost(String pathSegment, @Nullable Object request, Class<T> responseType)
        throws ApiClientException {
        return super.postForObject(toUriString(pathSegment), request, responseType);
    }

    /**
     * @param pathSegment
     * @param responseType
     * @param uriParams
     * @param <T>
     * @return
     * @throws ApiClientException
     */
    @Nullable
    public <T> T doGet(String pathSegment, Class<T> responseType, Object... uriParams) throws ApiClientException {
        // 1. getForObject(url, classType)
        return super.getForObject(toUriString(pathSegment), responseType, uriParams);

        // 2. getForEntity(url, responseType)
//        return Arrays.asList(restService.getForEntity(USERS, UserDTO[].class).getBody());

        // 3. exchange(url, method, requestEntity, responseType)
//        final HttpEntity<String> httpEntity = new HttpEntity<>(BaseService.newHttpHeaders());
//        return Arrays.asList(restService.exchange(USERS, HttpMethod.GET, httpEntity, UserDTO[].class).getBody());
    }

    /**
     * @param pathSegment
     * @param responseType
     * @param params
     * @param <T>
     * @return
     * @throws ApiClientException
     */
    @Nullable
    public <T> T doGet(String pathSegment, Class<T> responseType, Map<String, ?> params)
        throws ApiClientException {
        LOGGER.debug("doGet({}, {}, {})", pathSegment, responseType, params);
        return super.getForObject(toUriString(pathSegment, params), responseType, params);
    }

    /**
     * @param pathSegment
     * @param responseType
     * @param <T>
     * @return
     * @throws ApiClientException
     */
    @Nullable
    public <T> T doGet(String pathSegment, Class<T> responseType) throws ApiClientException {
        return super.getForObject(toUriString(pathSegment), responseType);
    }

    /**
     * @param pathSegment
     * @param request
     * @param uriParams
     * @throws ApiClientException
     */
    public void doPut(String pathSegment, @Nullable Object request, Object... uriParams) throws ApiClientException {
        super.put(toUriString(pathSegment), request, uriParams);
    }

    /**
     * @param pathSegment
     * @param request
     * @param uriParams
     * @throws ApiClientException
     */
    public void doPut(String pathSegment, @Nullable Object request, Map<String, ?> uriParams)
        throws ApiClientException {
        super.put(toUriString(pathSegment, uriParams), request, uriParams);
    }

    /**
     * @param pathSegment
     * @param request
     * @throws ApiClientException
     */
    public void doPut(String pathSegment, @Nullable Object request) throws ApiClientException {
        super.put(toUriString(pathSegment), request);
    }

    /**
     * @param pathSegment
     * @param uriParams
     * @throws ApiClientException
     */
    public void doDelete(String pathSegment, Object... uriParams) throws ApiClientException {
        super.delete(toUriString(pathSegment), uriParams);
    }

    /**
     * @param pathSegment
     * @param uriParams
     * @throws ApiClientException
     */
    public void doDelete(String pathSegment, Map<String, ?> uriParams) throws ApiClientException {
        super.delete(toUriString(pathSegment, uriParams), uriParams);
    }

    /**
     * @param pathSegment
     * @throws ApiClientException
     */
    public void doDelete(String pathSegment) throws ApiClientException {
        super.delete(toUriString(pathSegment));
    }

}
