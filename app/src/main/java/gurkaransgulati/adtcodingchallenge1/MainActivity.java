package gurkaransgulati.adtcodingchallenge1;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class MainActivityViewHolder{
    View view;
    ListView listView;
    NYTListViewAdapter nytListViewAdapter;
    ArrayList<Story> stories;
}


public class MainActivity extends Activity {

    Activity activity;
    Window window;
    MainActivityViewHolder mainActivityViewHolder;
    Drawable fallback;
    DisplayImageOptions defaultOptions;
    ImageLoaderConfiguration config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        setupBackground();
        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setup(){
        activity = this;
        window = getWindow();
        mainActivityViewHolder = new MainActivityViewHolder();
        setContentView(R.layout.activity_main);
        mainActivityViewHolder.view = findViewById(android.R.id.content);
        mainActivityViewHolder.listView = (ListView) findViewById(R.id.listView);
        mainActivityViewHolder.stories = new ArrayList<Story>();
        fallback = getDrawable(R.drawable.default1);
        // UNIVERSAL IMAGE LOADER SETUP
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        config = new ImageLoaderConfiguration.Builder(
                activity.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);


    }

    private void setupBackground(){

    }

    private void getData(){

        String stringJsonResponse = null;
        try {
            stringJsonResponse = new AsyncTaskAPIGetData().execute().get().toString().replace("\\","/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        JsonReader jsonReader = new JsonReader(new StringReader(stringJsonResponse));
//        jsonReader.setLenient(true);
//        JsonElement jsonElementResponse = gson.fromJson(jsonReader,JsonElement.class);
//        JsonObject jsonObjectResponse = jsonElementResponse.getAsJsonObject();

        System.out.println(stringJsonResponse);
        stringJsonResponse = "{'a':'b','c':'d'}";
        System.out.println(stringJsonResponse);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObjectResponse = jsonParser.parse(stringJsonResponse).getAsJsonObject();
        if (jsonObjectResponse != null){
            String status = jsonObjectResponse.get("status").getAsString();

            if (status != null && status == "OK"){
                JsonArray jsonArrayResponseResults = jsonObjectResponse.get("results").getAsJsonArray();
                if (jsonArrayResponseResults != null){
                    for(int i = 0; i < jsonArrayResponseResults.size(); i++){
                        JsonObject jsonObjectStory = jsonArrayResponseResults.get(i).getAsJsonObject();
                        if (jsonObjectStory != null){
                            Story newStory = new Story(jsonObjectStory);
                            mainActivityViewHolder.stories.add(newStory);
                        }
                    }
                    mainActivityViewHolder.nytListViewAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    class AsynTaskSetupBackground extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (mainActivityViewHolder.nytListViewAdapter == null){
                mainActivityViewHolder.nytListViewAdapter = new NYTListViewAdapter(mainActivityViewHolder.stories,activity);
            }
            if (mainActivityViewHolder.listView != null){
                mainActivityViewHolder.listView.setAdapter(mainActivityViewHolder.nytListViewAdapter);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
