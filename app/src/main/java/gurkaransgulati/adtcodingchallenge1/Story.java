package gurkaransgulati.adtcodingchallenge1;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gurkarangulati on 7/28/15.
 */
public class Story extends Object {

    String section;
    String subsection;
    String title;
    String abstr; //abstract is a keyword so using abstr
    URL urlArticle;
    String byline;
    Date dateUpdated;
    Date dateCreated;
    Date datePublished;
    ArrayList<StandardThumbnail> standardThumbnails;


    public Story(JsonObject jsonObject){

        section = jsonObject.get("section").getAsString();
        subsection = jsonObject.get("subsection").getAsString();
        title = jsonObject.get("title").getAsString();
        abstr = jsonObject.get("abstract").getAsString();
        try {
            urlArticle = new URL(jsonObject.get("url").getAsString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        byline = jsonObject.get("byline").getAsString();
        section = jsonObject.get("section").getAsString();
        JsonArray multimedia_array = jsonObject.get("multimedia").getAsJsonArray();
        if (multimedia_array != null){
            standardThumbnails = new ArrayList<StandardThumbnail>();
            for (int i = 0; i < multimedia_array.size(); i++){
                JsonObject jsonObjectStandardThumbnail = null;
                    jsonObjectStandardThumbnail = multimedia_array.get(i).getAsJsonObject();
                if (jsonObjectStandardThumbnail != null){
                    StandardThumbnail standardThumbnail = new StandardThumbnail(jsonObjectStandardThumbnail);
                    standardThumbnails.add(standardThumbnail);
                }
            }
        }


    }

}
