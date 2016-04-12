import com.googlecode.jsonrpc4j.JsonRpcServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Copyright (c) 2015 Rafal Macyszyn.
 */
public class MyJsonRpcServer extends HttpServlet {

        private static Handler handler;
        private static JsonRpcServer jsonRpcServer;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            jsonRpcServer.handle(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(ServletConfig config) {
        handler = new HandlerImpl();
        jsonRpcServer = new JsonRpcServer(handler);
    }

    public static void main(String[] argv) throws Exception {
        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new MyJsonRpcServer()), "/*");

        server.setHandler(context);

        server.start();
        server.join();
    }

}
