package com.example.wikitea.Tables.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikitea.R;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminHolder> {
    private List<Admin> admins = new ArrayList<>();


    @NonNull
    @Override
    public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHolder holder, int position) {
        Admin currentAdmin = admins.get(position);
        holder.textViewName.setText(currentAdmin.getName());
        holder.textViewPassword.setText(currentAdmin.getPassword());
        holder.textViewId.setText(String.valueOf(currentAdmin.getId()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public void setAdmins(List<Admin> admins) {

        this.admins = admins;
        notifyDataSetChanged();
    }

    public Admin getAdminAt(int position) {

        return admins.get(position);
    }


    class AdminHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewPassword;
        private TextView textViewId;

        public AdminHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPassword = itemView.findViewById(R.id.text_view_password);
            textViewId = itemView.findViewById(R.id.text_view_id);





        }
    }


}
