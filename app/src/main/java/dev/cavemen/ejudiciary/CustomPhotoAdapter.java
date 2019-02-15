package dev.cavemen.ejudiciary;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomPhotoAdapter extends PagerAdapter {

    private int images[]={R.drawable.viewpagerimage,R.drawable.download};
    private Context ctx;
    private LayoutInflater layoutInflater;
    public CustomPhotoAdapter(Context ctx){
        this.ctx=ctx;

    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item=layoutInflater.inflate(R.layout.swipepictures,container,false);
        ImageView imageView=(ImageView)item.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
