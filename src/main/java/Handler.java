/*
 * Copyright (c) 2015 Rafal Macyszyn.
 */

import com.googlecode.jsonrpc4j.JsonRpcService;

import java.util.List;

public interface Handler {
    List<String> showServices();
    List<String> getServiceArgs(String name);
    String getText();
    boolean setText(String text);
    int dodajTrzyLiczby(int n1, int n2, int n3);

    String currentTime();
    String currentTimeFormat(String format);
    int currentTimestamp();
}
