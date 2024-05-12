package com.example.onlinemeditationapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class SessionItemAdapter extends RecyclerView.Adapter<SessionItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<SessionItem> sessionItems;
    private ArrayList<SessionItem> sessionItemsAll;
    private Context context;
    private int lastPosition = -1;


    SessionItemAdapter(Context context, ArrayList<SessionItem> items){
        this.sessionItems = items;
        this.sessionItemsAll = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.session_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SessionItemAdapter.ViewHolder holder, int position) {
        SessionItem currentItem = sessionItems .get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return sessionItems.size();
    }

    @Override
    public Filter getFilter() {
        return sessionFilter;
    }

    private Filter sessionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<SessionItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                results.count = sessionItemsAll.size();
                results.values = sessionItemsAll;
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(SessionItem item : sessionItemsAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sessionItems = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView desc;
        private TextView price;
        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.sessionTitle);
            desc = itemView.findViewById(R.id.sessionDesc);
            price = itemView.findViewById(R.id.sessionPrice);

            itemView.findViewById(R.id.sessionRegisterButton).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("Activity", "Session register button pressed");
                }
            });
        }

        public void bindTo(SessionItem currentItem){
            title.setText(currentItem.getName());
            desc.setText(currentItem.getDesc());
            price.setText(currentItem.getPrice());
        }
    }

}

