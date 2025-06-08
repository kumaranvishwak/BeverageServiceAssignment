package de.uniba.dsg.jaxrs;

import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

public class JaxRsServer {

    public static void main(String[] args) throws IOException {
        // Use either hardcoded or loaded from config
//        // 🔧 Option 1: Use config file
//        Properties properties = Configuration.loadProperties();
//        String serverUri = properties.getProperty("serverUri", "http://localhost:8080/");

        // 🔧 Option 2: Use hardcoded URI instead:
        String serverUri = "http://localhost:8080/";

        URI baseUri = UriBuilder.fromUri(serverUri).build();

        // Register your application + Jackson for JSON
        ResourceConfig config = ResourceConfig.forApplicationClass(ExamplesApi.class);
        config.register(JacksonFeature.class); // ✅ Needed for application/json support

        // Start server
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);

        System.out.println("✅ Server started at " + serverUri);
        System.out.println("Press any key to stop.");
        System.in.read();

        // Stop server
        System.out.println("🔴 Stopping server...");
        server.stop(1);
    }
}
