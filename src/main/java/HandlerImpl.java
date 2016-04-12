import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Copyright (c) 2015 Rafal Macyszyn.
 */

public class HandlerImpl implements Handler {
    public static String savedText = "";
    private final HashMap<String, List<String>> methods = new HashMap<String, List<String>>();

    public HandlerImpl() {
        for (Method method : HandlerImpl.class.getMethods()) {
            if (method.isAnnotationPresent(VisibleInShow.class)) {

                ArrayList<String> args = new ArrayList<String>();
                args.add(method.getReturnType().getName());
                for (Class klass : method.getParameterTypes()) {
                    args.add(klass.getName());
                }
                methods.put(method.getName(), args);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface VisibleInShow { }

    @VisibleInShow
    public List<String> showServices() {
        List<String> ret = new ArrayList<String>();
        for (String name : methods.keySet()) {
            ret.add(name);
        }
        return ret;
    }

    @VisibleInShow
    public List<String> getServiceArgs(String name) {
        try {
            return methods.get(name);
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    @VisibleInShow
    public String getText() {
        System.out.println("DUPADUPA");
        return savedText;
    }

    @VisibleInShow
    public boolean setText(String text) {
        savedText = text;
        return true;
    }

    @HandlerImpl.VisibleInShow
    public int dodajTrzyLiczby(int n1, int n2, int n3) {
        return n1 + n2 + n3;
    }

    @HandlerImpl.VisibleInShow
    public String currentTimeFormat(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    @HandlerImpl.VisibleInShow
    public String currentTime() {
        return currentTimeFormat("HH:mm:ss");
    }

    public int currentTimestamp() {
        return 0;
    }
}
