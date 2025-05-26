package com.teliolabs.tejas.ptn.config;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import com.teliolabs.tejas.ptn.exception.*;

import reactor.core.publisher.Mono;

@Slf4j
public class WebClientStatusCodeHandler {

    public static final String RES_BODY = "Response Body: {}";

    private WebClientStatusCodeHandler() {
    }


    public static Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        HttpStatus status = (HttpStatus) response.statusCode();
        log.debug("Returned status code {} ({})", status.value(), status.getReasonPhrase());
        if (status != null && (status.is4xxClientError() || status.is5xxServerError())) {
            if (HttpStatus.BAD_REQUEST.equals(status)) {
                return response.bodyToMono(String.class).defaultIfEmpty(status.getReasonPhrase()).flatMap(body -> {
                    log.error(RES_BODY, body);
                    return Mono.error(new BadRequestException("BadRequestException", body));
                });
            } else if (HttpStatus.NOT_FOUND.equals(status)) {
                return response.bodyToMono(String.class).defaultIfEmpty(status.getReasonPhrase()).flatMap(body -> {
                    log.error(RES_BODY, body);
                    return Mono.error(new NotFoundException("NotFoundException", body));
                });
            } else if (HttpStatus.SERVICE_UNAVAILABLE.equals(status)) {
                return response.bodyToMono(String.class).defaultIfEmpty(status.getReasonPhrase()).flatMap(body -> {
                    log.error(RES_BODY, body);
                    return Mono.error(new NotAvailableException("NotAvailableException", body));
                });
            } else if (HttpStatus.UNAUTHORIZED.equals(status)) {
                return response.bodyToMono(String.class).defaultIfEmpty(status.getReasonPhrase()).flatMap(body -> {
                    log.error(RES_BODY, body);
                    return Mono.error(new UnAuthorizedException("UnAuthorizedException", body));
                });
            } else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
                return response.bodyToMono(String.class).defaultIfEmpty(status.getReasonPhrase()).flatMap(body -> {
                    log.error(RES_BODY, body);
                    return Mono.error(new InternalServerException("InternalServerException", body));
                });
            } else {
                return response.bodyToMono(String.class).defaultIfEmpty(status.getReasonPhrase()).flatMap(body -> {
                    log.error(RES_BODY, body);
                    return Mono.error(new ApiException("ApiException", body));
                });
            }
        } else {
            return Mono.just(response);
        }
    }


    /**
     * Logging filter that logs response
     *
     * @return
     */
    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logStatus(clientResponse);
            logHeaders(clientResponse);
            if (log.isTraceEnabled()) logCookies(clientResponse);
            return Mono.just(clientResponse);
        });
    }

    /**
     * Logging filter that logs request
     *
     * @return
     */
    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logMethodAndUrl(clientRequest);
            logHeaders(clientRequest);
            if (log.isTraceEnabled()) logCookies(clientRequest);
            return Mono.just(clientRequest);
        });
    }

    private static void logHeaders(ClientRequest request) {
        log.debug("ClientRequest Headers .." + request.logPrefix());
        request.headers().forEach((name, values) -> values.forEach(value -> logNameAndValuePair(name, value)));
    }

    private static void logCookies(ClientRequest request) {
        request.cookies().forEach((name, values) -> values.forEach(value -> logNameAndValuePair(name, value)));
    }

    private static void logCookies(ClientResponse request) {
        request.cookies().forEach((name, values) -> values.forEach(value -> logNameAndValuePair(name, value.toString())));
    }

    private static void logHeaders(ClientResponse response) {
        log.debug("ClientResponse Headers .." + response.logPrefix());
        response.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> logNameAndValuePair(name, value)));
    }

    private static void logNameAndValuePair(String name, String value) {
        log.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        log.debug("{}={}", name, value);
    }

    private static void logMethodAndUrl(ClientRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.method().name());
        sb.append(" to ");
        sb.append(request.url());

        log.debug(sb.toString());
    }

    private static void logStatus(ClientResponse response) {
        HttpStatus status = response.statusCode();
        log.debug("Returned status code {} ", status.value());
    }
}
