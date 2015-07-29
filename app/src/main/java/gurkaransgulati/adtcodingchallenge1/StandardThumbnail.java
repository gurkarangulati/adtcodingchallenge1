package gurkaransgulati.adtcodingchallenge1;

import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gurkarangulati on 7/28/15.
 */
public class StandardThumbnail extends Object {

    URL url;
    Integer height;
    Integer width;
    String type;
    String subtype;
    String caption;
    String copyright;

    public StandardThumbnail(JsonObject jsonObject){
        try {
            url = new URL(jsonObject.get("url").getAsString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        height = jsonObject.get("height").getAsInt();
        width = jsonObject.get("width").getAsInt();
        type = jsonObject.get("type").getAsString();
        subtype = jsonObject.get("subtype").getAsString();
        caption = jsonObject.get("caption").getAsString();
        copyright = jsonObject.get("copyright").getAsString();

    }
}
