package io.github.riwcwt.config;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by michael on 2016-12-29.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = TomcatEmbeddedServletContainerFactory.class.cast(container);
                tomcat.addConnectorCustomizers(connector -> {
                    ProtocolHandler handler = connector.getProtocolHandler();
                    if (handler instanceof Http11NioProtocol) {
                        Http11NioProtocol protocol = Http11NioProtocol.class.cast(handler);
                        //设置最大连接数
                        protocol.setMaxConnections(10000);
                        //设置最大线程数
                        protocol.setMaxThreads(1000);
                        protocol.setConnectionTimeout(30000);
                    }
                });
            }
        };
    }

}
