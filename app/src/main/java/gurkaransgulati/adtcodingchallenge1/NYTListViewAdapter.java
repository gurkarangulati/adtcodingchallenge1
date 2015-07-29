package gurkaransgulati.adtcodingchallenge1;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by gurkarangulati on 7/28/15.
 */

class NYTListViewViewHolder{
    Story story;
    TextView textView;
    ImageView imageView;
}

public class NYTListViewAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<Story> stories;
    static LayoutInflater inflater = null;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    Drawable fallback;
    View finalView;
    NYTListViewViewHolder nytListViewViewHolder;

    public NYTListViewAdapter(ArrayList<Story> stories, Activity activity){
        this.activity = activity;
        this.stories = stories;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = activity.getResources();
        fallback = resources.getDrawable(R.drawable.default1, null);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        finalView = null;
        Boolean alreadyLoaded;

        if (convertView == null) {
            finalView = inflater.inflate(
                    R.layout.activity_main_listview_item, parent, false);
            nytListViewViewHolder= new NYTListViewViewHolder();
            alreadyLoaded = false;
        } else {

            finalView = convertView;
            alreadyLoaded = true;
        }
        nytListViewViewHolder.story = stories.get(position);
        nytListViewViewHolder.textView = (TextView) finalView
                .findViewById(R.id.textView); // title
        nytListViewViewHolder.imageView = (ImageView) finalView
                .findViewById(R.id.imageView);
        imageLoader.displayImage(nytListViewViewHolder.story.standardThumbnails.get(0).url.toString(),nytListViewViewHolder.imageView);
        return null;
    }
}
