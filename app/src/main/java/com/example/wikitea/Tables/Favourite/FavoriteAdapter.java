package com.example.wikitea.Tables.Favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavouriteHolder>{


    private List<Favorite> favorites = new ArrayList<>();
    private FavoriteAdapter.OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private FavoriteAdapter.OnItemClickListener clickListener;

    @NonNull
    @Override
    public FavoriteAdapter.FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item,parent,false);
        return new FavoriteAdapter.FavouriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteHolder holder, int position) {
        Favorite currentFavourite = favorites.get(position);
        holder.textViewTitle.setText(currentFavourite.getTitle());
        holder.textViewDescription.setText(currentFavourite.getDescription());
        holder.textViewId.setText(String.valueOf(currentFavourite.getIdFavorite()));

    }


    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void setFavorites(List<Favorite> favorites)
    {
        this.favorites = favorites;
        notifyDataSetChanged();

    }

    public Favorite getFavouriteAt(int position)
    {
        return favorites.get(position);
    }


    class FavouriteHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewId;

        public FavouriteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewId = itemView.findViewById(R.id.text_view_id);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(longClickListener != null && position != RecyclerView.NO_POSITION){
                        longClickListener.onItemLongClick(favorites.get(position));
                    }
                    return false;
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(clickListener != null && position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(favorites.get(position));
                    }
                }
            });

        }
    }


    //Long click
    public interface OnItemLongClickListener {
        void onItemLongClick(Favorite favorite);
    }
    public void setOnItemLongClickListener(FavoriteAdapter.OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }


    public interface OnItemClickListener{
        void onItemClick(Favorite favourite);
    }
    public void setOnItemClickListener(FavoriteAdapter.OnItemClickListener listener) {
        this.clickListener = listener;
    }

}
