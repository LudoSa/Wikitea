package com.example.wikitea;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wikitea.R;

//Class for manage the listview of the homeActivity
public class TeaListView extends ArrayAdapter<String> {

    private String[] teaname;
    private String[] teadesc;
    private Integer[] imgid;
    private Activity context;

    public TeaListView(Activity context, String[] teaname, String[] teadesc, Integer[] imgid ){
        super(context, R.layout.listview_layout, teaname);

        this.context=context;
        this.teaname=teaname;
        this.teadesc=teadesc;
        this.imgid=imgid;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
            ViewHolder viewHolder=null;
            if(r==null)
            {

                LayoutInflater layoutInflater=context.getLayoutInflater();
                r=layoutInflater.inflate(R.layout.listview_layout,null,true);
                viewHolder=new ViewHolder(r);
                r.setTag(viewHolder);
            }
            else
            {
                viewHolder=(ViewHolder) r.getTag();
            }

            viewHolder.img.setImageResource(imgid[position]);
            viewHolder.name.setText(teaname[position]);
            viewHolder.desc.setText(teadesc[position]);
        return r;
    }

    class ViewHolder
    {
        TextView name;
        TextView desc;
        ImageView img;
        ViewHolder(View v)
        {
            name=v.findViewById(R.id.teaname);
            desc=v.findViewById(R.id.teadescription);
            img=v.findViewById(R.id.imageView);
        }
    }

    public interface TeaListener{

    }
}
