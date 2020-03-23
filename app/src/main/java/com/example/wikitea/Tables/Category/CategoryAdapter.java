package com.example.wikitea.Tables.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.R;
import com.example.wikitea.Tables.Tea.Tea;
import com.example.wikitea.Tables.Tea.TeaAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> categories = new ArrayList<>();
    private OnItemLongClickListener longClickListener;
    private OnItemClickListener clickListener;

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.textViewName.setText(currentCategory.getName());
        holder.textViewVirtues.setText(currentCategory.getVirtues());
        holder.textViewId.setText(String.valueOf(currentCategory.getId()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public void setCategories(List<Category> categories) {

        this.categories = categories;
        notifyDataSetChanged();
    }

    public Category getCategoryAt(int position) {

        return categories.get(position);
    }


    class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewVirtues;
        private TextView textViewId;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewVirtues = itemView.findViewById(R.id.text_view_virtues);
            textViewId = itemView.findViewById(R.id.text_view_id);

            //Modify category by loooong clicking
            itemView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(longClickListener != null && position != RecyclerView.NO_POSITION){
                        longClickListener.onItemLongClick(categories.get(position));
                    }
                    return false;
                }
            });

            //Simple click to open new View
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(clickListener != null && position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(categories.get(position));
                    }
                }
            });



        }
    }



    //Long click
    public interface OnItemLongClickListener {
        void onItemLongClick(Category category);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }


    //Simple click on an item
    public interface OnItemClickListener{
        void onItemClick(Category category);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }


}
