package com.teliolabs.tejas.ptn.config;


import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;


@Slf4j
@Configuration
public class WebClientConfig {

    SslContext sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build();

    HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

    public WebClientConfig() throws SSLException {
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        final int size = 256 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        ExchangeFilterFunction errorResponseFilter = ExchangeFilterFunction
                .ofResponseProcessor(WebClientStatusCodeHandler::exchangeFilterResponseProcessor);
        return WebClient.
                builder().
                exchangeStrategies(strategies).
                clientConnector(new ReactorClientHttpConnector(httpClient)).
                filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(WebClientStatusCodeHandler.logRequest());
                    exchangeFilterFunctions.add(WebClientStatusCodeHandler.logResponse());
                    exchangeFilterFunctions.add(errorResponseFilter);
                });
    }
}
