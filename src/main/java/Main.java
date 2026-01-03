import com.dampcake.bencode.Type;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.dampcake.bencode.Bencode;

import static java.lang.System.out;

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
                out.println("Error in decoding args[1]");
                out.println(e.getMessage());
                return;
            }
            out.println(gson.toJson(decoded));
        }
        else if("info".equals(command)){
            String filePath=args[1];
            try{
                FileInputStream fileInputStream=new FileInputStream(filePath);
                Bencode bencode=new Bencode();
                Map<String,Object> torrentFileMap= bencode.decode(fileInputStream.readAllBytes(), Type.DICTIONARY);
                String trackerURL=(String)torrentFileMap.get("announce");
                Long length=(Long)((Map<String, Object>)torrentFileMap.get("info")).get("length");

                out.println("Tracker URL: "+trackerURL);
                out.println("Length: "+length);
            }
            catch (Exception e){
                out.println("Error in reading torrent file");
                out.println(e.getMessage());
                return;
            }
        }
        else {
            out.println("Unknown command: " + command);
        }

    }



}
