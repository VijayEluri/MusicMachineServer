package com.markupartist.nollbit.musicmachine.server.handlers;

import com.markupartist.nollbit.musicmachine.server.MusicMachineApplication;
import com.markupartist.nollbit.musicmachine.server.MusicMachineHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.felixbruns.jotify.gateway.GatewayApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: johanm
 * Date: Apr 14, 2010
 * Time: 10:27:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentHandler implements HttpHandler {
    private static Map<String, String> contentTypes;

    static {
        contentTypes = new HashMap<String, String>();

        contentTypes.put("html", "text/html");
        contentTypes.put("css",  "text/css");
        contentTypes.put("js",   "text/javascript");
        contentTypes.put("png",  "image/png");
        contentTypes.put("gif",  "image/gif");
        contentTypes.put("jpg",  "image/jpeg");
    }

    public void handle(HttpExchange exchange) throws IOException {
        Headers headers  = exchange.getResponseHeaders();
        OutputStream body     = exchange.getResponseBody();
        String       path     = exchange.getRequestURI().getPath();
        byte[]       buffer   = new byte[8192];
        int          read;
        String       resource;

        /* Get resource path. */
        if(path.equals("/")){
            resource = "resources/index.html";
        }
        else{
            resource = "resources" + path;
        }

        /* Get Content-Type. */
        String extension   = resource.substring(resource.lastIndexOf('.') + 1);
        String contentType = contentTypes.get(extension);

        //System.out.format("GET %s\n", resource);

        /* Get resource as stream. */
        InputStream stream = MusicMachineApplication.class.getResourceAsStream(resource);
		
        /* Check if resource was found. */
        if(stream != null){
            /* Set headers. */
            headers.set("Content-Type", contentType);
            headers.set("Cache-Control", "max-age=604800");

            /* Send response header with Content-Length. */
            exchange.sendResponseHeaders(200, stream.available());

            /* Write response to output stream. */
            while((read = stream.read(buffer)) != -1){
                body.write(buffer, 0, read);
            }
        }
        else{
            byte[] bytes = "404 Not Found".getBytes();

            /* Send response header with Content-Length. */
            exchange.sendResponseHeaders(404, bytes.length);

            body.write(bytes);
        }

        /* Close. */
        body.close();
    }
}
