import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;
import com.sun.deploy.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/*
 * Copyright (c) 2015 Rafal Macyszyn.
 */
public class MyJsonRpcClient {

    private static final Scanner scanner = new Scanner(System.in);

    public static List<String> convertToArrayOfStrings(Object object) {
        List<String> ret = new ArrayList<String>();

        for (Object o : (Object[]) object) {
            ret.add((String) o);
        }

        return ret;
    }

    public static void main(String[] argv) throws Throwable {

        System.out.print("Podaj adres serwera:\n> ");
//        URL url = new URL(scanner.next());
        URL url = new URL("http://localhost:8081/");

        JsonRpcHttpClient client = new JsonRpcHttpClient(url);

        Handler handler = ProxyUtil.createClientProxy(ClassLoader.getSystemClassLoader(), Handler.class, client);

        System.out.println("\nPrzykład 1: ustawienie tekstu z linii poleceń i pobranie go");
        try {
            handler.setText("dupa");
            System.out.println(">> " + handler.getText());

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println("\nPrzykład 2: jakie parametry (return type, arg1, arg2...) ma metoda setText?");
        System.out.println(StringUtils.join(handler.getServiceArgs("setText"), ", "));

        System.out.println("\nPrzykład 2a: jakie parametry (return type, arg1, arg2...) ma metoda getText?");
        System.out.println(StringUtils.join(handler.getServiceArgs("getText"), ", "));


        System.out.println("\nPrzykład 3: lista obsługiwanych metod");
        System.out.println(StringUtils.join(handler.showServices(), ", "));

        System.out.println("\nPrzykład 4: zawołaj własną metodę");
        System.out.print("Nazwa metody:\n> ");
        String methodName = scanner.next();

        List<Object> params = new ArrayList<Object>();

        List<String> argsList = client.invoke("getServiceArgs", new Object[]{methodName}, List.class);
        Iterator<String> argsListIter = argsList.listIterator();

        System.out.println(methodName + "(...) -> " + argsListIter.next());

        List<Object> args = new ArrayList<Object>();


        while (argsListIter.hasNext()) {
            String argType = argsListIter.next();

            if (argType.equals("java.lang.String")) {
                System.out.print("string> ");
                args.add(scanner.next());
            } else if (argType.equals("boolean")) {
                System.out.print("bool> ");
                args.add(scanner.nextBoolean());
            } else if (argType.equals("int")) {
                System.out.print("int> ");
                args.add(scanner.nextInt());
            }
        }

        Object result = client.invoke(methodName, args, Object.class);
        System.out.println(">> " + result.toString());
    }
}
