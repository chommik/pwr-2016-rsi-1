import com.sun.deploy.util.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/*
 * Copyright (c) 2015 Rafal Macyszyn.
 */
public class MyXmlRpcClient {

    private static final Scanner scanner = new Scanner(System.in);
    private static final XmlRpcClient client = new XmlRpcClient();

    public static List<String> convertToArrayOfStrings(Object object) {
        List<String> ret = new ArrayList<String>();

        for (Object o : (Object[]) object) {
            ret.add((String) o);
        }

        return ret;
    }

    public static void main(String[] argv) throws XmlRpcException, MalformedURLException {

        System.out.print("Podaj adres serwera:\n> ");
        URL url = new URL(scanner.next());

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(url);
        client.setConfig(config);

        System.out.println("\nPrzykład 1: ustawienie tekstu z linii poleceń i pobranie go");
        System.out.print("Ustaw tekst na:\n> ");
        client.execute("api.setText", new Object[]{ scanner.next() });
        String resultString = (String) client.execute("api.getText", new Object[]{});
        System.out.println(resultString);

        System.out.println("\nPrzykład 2: jakie parametry (return type, arg1, arg2...) ma metoda setText?");
        Object result = client.execute("api.getServiceArgs", new Object[]{"setText"});
        List<String> resultList = convertToArrayOfStrings(result);
        System.out.println(StringUtils.join(resultList, ", "));

        System.out.println("\nPrzykład 2a: jakie parametry (return type, arg1, arg2...) ma metoda getText?");
        result = client.execute("api.getServiceArgs", new Object[]{"getText"});
        resultList = convertToArrayOfStrings(result);
        System.out.println(StringUtils.join(resultList, ", "));

        System.out.println("\nPrzykład 3: lista obsługiwanych metod");
        result = client.execute("api.showServices", new Object[]{});
        resultList = convertToArrayOfStrings(result);
        System.out.println(StringUtils.join(resultList, ", "));

        System.out.println("\nPrzykład 4: zawołaj własną metodę");
        System.out.print("Nazwa metody:\n> ");
        String methodName = scanner.next();

        List<Object> params = new ArrayList<Object>();

        result = client.execute("api.getServiceArgs", new Object[]{methodName});
        List<String> argsList = convertToArrayOfStrings(result);
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

        result = client.execute("api." + methodName, args);
        System.out.println(">> " + result.toString());

    }
}
