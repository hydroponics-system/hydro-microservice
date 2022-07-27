package com.hydro.factory.abstracts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;
import com.hydro.insite_jwt_microservice.utility.JwtTokenUtil;
import com.hydro.insite_user_microservice.client.domain.User;

/**
 * Base Test class for controllers performing rest endpoint calls.
 * 
 * @author Sam Butler
 * @since July 27, 2021
 */
public abstract class BaseControllerTest extends RequestTestUtil {

    @LocalServerPort
    protected int randomServerPort;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private HttpHeaders headers;

    @BeforeEach
    public void initHeaders(TestInfo info) {
        headers = new HttpHeaders();

        ControllerJwt annClass = AnnotatedElementUtils.findMergedAnnotation(info.getTestClass().get(),
                                                                            ControllerJwt.class);
        ControllerJwt annMethod = AnnotatedElementUtils.findMergedAnnotation(info.getTestMethod().get(),
                                                                             ControllerJwt.class);

        if(annMethod != null) {
            setHeaders(annMethod);
        }
        else if(annClass != null) {
            setHeaders(annClass);
        }
    }

    @AfterEach
    public void clearHeaders() {
        headers = new HttpHeaders();
    }

    /**
     * Perform a get call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> get(String api, Class<T> responseType) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return exchange(api, HttpMethod.GET, requestEntity, responseType);
    }

    /**
     * Perform a post call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> post(String api, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        return exchange(api, HttpMethod.POST, requestEntity, responseType);
    }

    /**
     * Perform a post call on the given api.
     * 
     * @param <T>          The response type of the call.
     * @param api          The endpoint to consume.
     * @param request      The request to send with the post.
     * @param responseType What the object return should be cast as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> post(String api, Object request, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(request, headers);
        return exchange(api, HttpMethod.POST, requestEntity, responseType);
    }

    /**
     * Make an exchange call through the rest template.
     * 
     * @param <T>    Typed parameter of the response type.
     * @param api    The api to hit.
     * @param method The method to perform on the endpoint.
     * @param entity The entity instance to pass.
     * @param clazz  The class to return the response as.
     * @return Response entity of the returned data.
     */
    protected <T> ResponseEntity<T> exchange(String api, HttpMethod method, HttpEntity<?> entity, Class<T> clazz) {
        return testRestTemplate.exchange(buildUrl(api), method, entity, clazz);
    }

    /**
     * Build out the absolute path for the api.
     * 
     * @param api The api to build.
     * @return Completed url with the attached api.
     */
    private String buildUrl(String api) {
        return String.format("http://localhost:%d%s", randomServerPort, api);
    }

    /**
     * Set headers on instance class. Used for authenticated endpoint calls.
     * 
     * @param jwtController The annotation containing the user information.
     */
    private void setHeaders(ControllerJwt jwtController) {
        User u = new User();
        u.setId(jwtController.userId());
        u.setFirstName(jwtController.firstName());
        u.setLastName(jwtController.lastName());
        u.setEmail(jwtController.email());
        u.setWebRole(jwtController.webRole());

        String token = jwtTokenUtil.generateToken(u);
        headers.set("Authorization", "Bearer: " + token);
    }
}
