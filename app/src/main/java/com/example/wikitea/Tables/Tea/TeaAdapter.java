package com.example.wikitea.Tables.Tea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.R;

import java.util.ArrayList;
import java.util.List;

public class TeaAdapter extends RecyclerView.Adapter<TeaAdapter.TeaHolder> {

    private List<Tea> teas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TeaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tea_item,parent,false);
        return new TeaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaHolder holder, int position) {

        Tea currentTea = teas.get(position);
        holder.textViewTitle.setText(currentTea.getTitle());
        holder.textViewDescription.setText(currentTea.getDescription());
        holder.textViewId.setText(String.valueOf(currentTea.getId()));
    }

    @Override
    public int getItemCount() {
        return teas.size();
    }

    public void setTeas(List<Tea> teas)
    {
        this.teas = teas;
        notifyDataSetChanged();

    }

    public Tea getTeaAt(int position)
    {
        return teas.get(position);
    }



    class TeaHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewId;

        public TeaHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewId = itemView.findViewById(R.id.text_view_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(teas.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Tea tea);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
