import com.dampcake.bencode.Type;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import com.dampcake.bencode.Bencode;

public class Main {
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws Exception {

        String command = args[0];
        if ("decode".equals(command)) {
            String bencodedValue = args[1];
            Object decoded;
            try {
                Bencode bencode=new Bencode();
                Type type = bencode.type(bencodedValue.getBytes());
                decoded = bencode.decode(bencodedValue.getBytes(), type);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(gson.toJson(decoded));

        } else {
            System.out.println("Unknown command: " + command);
        }

    }



}
