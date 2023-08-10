package com.milky.trackerWeb.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Value("${https.port}")
    private int serverPort;

    @Value("${keystore.file}")
    private String keystoreFile;

    @Value("${keystore.password}")
    private String keystorePassword;

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(org.apache.catalina.Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        factory.addAdditionalTomcatConnectors(createHttpsConnector());
        return factory;
    }

    private Connector createHttpsConnector() {
        Connector connector = new Connector(Http11NioProtocol.class.getName());
        connector.setScheme("https");
        connector.setPort(serverPort);
        connector.setSecure(true);
        connector.setProperty("SSLEnabled ", "true");
        connector.setProperty("keystoreFile", keystoreFile);
        connector.setProperty("keystorePass", keystorePassword);
        connector.setProperty("keyAlias", "mycert"); // Change this to your alias if different
        connector.setProperty("keystoreType", "jks");
        connector.setProperty("protocol", "TLS");
        return connector;
    }
}
