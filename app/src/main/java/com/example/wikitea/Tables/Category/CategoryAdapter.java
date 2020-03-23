package com.example.wikitea.Tables.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> categories = new ArrayList<>();
    private OnItemLongClickListener listener;

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

            //Modify category
            itemView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                    public boolean onLongClick(View v) {
                        int position = getAdapterPosition();
                        if(listener != null && position != RecyclerView.NO_POSITION){
                            listener.onItemLongClick(categories.get(position));
                        }
                        return false;
                }
            });

        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Category category);
    }

    public void setOnItemClickListener(OnItemLongClickListener listener) {
        this.listener = listener;

    }

}
