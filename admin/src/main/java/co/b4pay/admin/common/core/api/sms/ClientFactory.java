package co.b4pay.admin.common.core.api.sms;

import java.io.UnsupportedEncodingException;

public class ClientFactory {

    private static Client client;

    public static Client getInstance(String username, String password) throws UnsupportedEncodingException {
        if (client == null) {
            client = new Client(username, password);
        }
        return client;
    }
}
