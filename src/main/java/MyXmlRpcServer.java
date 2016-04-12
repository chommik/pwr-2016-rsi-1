import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

/*
 * Copyright (c) 2015 Rafal Macyszyn.
 */
public class MyXmlRpcServer {
    public static void main(String[] argv) throws XmlRpcException, IOException {
        System.out.println("Starting XML-RPC server.");

        WebServer ws = new WebServer(8080);
        XmlRpcServer xmlRpcServer = ws.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.addHandler("api", HandlerImpl.class);
        xmlRpcServer.setHandlerMapping(phm);

        ws.start();
    }
}
