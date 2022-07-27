package com.hydro.factory.abstracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydro.insite_common_microservice.exceptions.helper.ExceptionError;

/**
 * Request Test Util for common test functionality.
 * 
 * @author Sam Butler
 * @since July 27, 2022
 */
public abstract class RequestTestUtil {

    /**
     * Convenience method to execute a consumer on a page.
     * 
     * @param <T>            type of ResonseEntity
     * @param responseEntity to use
     * @param verifier       to use
     */
    public static <T> void check(ResponseEntity<T> responseEntity, Consumer<ResponseEntity<T>> verifier) {
        verifier.accept(responseEntity);
    }

    /**
     * Verify a ResponseEntity is of type list and that the provided length is
     * correct.
     * 
     * @param <T>        type of object from ResponseEntity
     * @param httpStatus Expected HttpStatus
     * @param count      The count of entities in the array
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T[]>> serializedList(HttpStatus httpStatus, int count) {
        return serialized(httpStatus, body -> {
            assertNotNull(body, "Returned");
            assertEquals(body.length, count, "Length of List");
        });
    }

    /**
     * Verify a ResponseEntity was provided where the HttpStatus was OK and the body
     * was not null.
     * 
     * @param <T> type of object from ResponseEntity
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T>> serializedNonNull() {
        return serializedNonNull(HttpStatus.OK);
    }

    /**
     * Verify a ResponseEntity was provided with the httpStatus provided and the
     * body was not null.
     * 
     * @param <T>        type of object from ResponseEntity
     * @param httpStatus Expected HttpStatus
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T>> serializedNonNull(HttpStatus httpStatus) {
        return serialized(httpStatus, body -> assertNotNull(body, "Returned"));
    }

    /**
     * Verify the ResponseEntity was provided with the httpStatus provided and the
     * corresponding error message was thrown.
     * 
     * @param <T>        type of object from ResponseEntity
     * @param httpStatus Expected HttpStatus
     * @param errorMsg   The message to expect
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T>> error(HttpStatus httpStatus, String errorMsg) {
        return serialized(body -> {
            ExceptionError e = new ObjectMapper().convertValue(body, ExceptionError.class);
            assertEquals(errorMsg, e.getMessage());
            assertEquals(httpStatus, e.getStatus());
        });
    }

    /**
     * Verify the ResponseEntity was provided with the httpStatus provided and pass
     * the body to the consumer provided in the body parameter.
     * 
     * @param <T>        type of object from ResponseEntity
     * @param httpStatus Expected HttpStatus
     * @param body       consumer that is provided the body from the ResponseEntity
     *                   provided
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T>> serialized(HttpStatus httpStatus, Consumer<T> body) {
        return responseEntity -> {
            assertHttpStatus(httpStatus, responseEntity);
            body.accept(responseEntity.getBody());
        };
    }

    /**
     * Verify the passed the body to the consumer provided in the body parameter.
     * 
     * @param <T>  type of object from ResponseEntity
     * @param body consumer that is provided the body from the ResponseEntity
     *             provided
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T>> serialized(Consumer<T> body) {
        return responseEntity -> body.accept(responseEntity.getBody());
    }

    /**
     * Verify the ResponseEntity was provided with httpStatus provided.
     * 
     * @param <T>        type of object from ResponseEntity
     * @param httpStatus Expected HttpStatus
     * @return Consumer to verify the response
     */
    public static <T> Consumer<ResponseEntity<T>> httpStatusEquals(HttpStatus httpStatus) {
        return responseEntity -> {
            assertHttpStatus(httpStatus, responseEntity);
        };
    }

    /**
     * Assert that the expected HttpStatus matches the ResponseEntity provides
     * status code.
     * 
     * @param expected Expected HttpStatus
     * @param given    ResponseEntity provided
     */
    public static void assertHttpStatus(HttpStatus expected, ResponseEntity<?> given) {
        assertEquals(expected, given.getStatusCode(), "Http Status " + expected.toString());
    }
}
