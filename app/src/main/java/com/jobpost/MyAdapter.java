package com.jobpost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<CategoryModel> data;
    private Context context;

    public MyAdapter(Context context, List<CategoryModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = data.get(position);
        holder.item_title.setText(category.getTitle());
        holder.item_description.setText(category.getDescription());
        holder.item_image.setImageResource(category.getCategoryImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                Intent intent;
                switch (currentPosition) {
                    case 0:
                        intent = new Intent(context, JobListingActivity.class);
                        break;
                    case 1:
                        intent = new Intent(context, CreateJobPost.class);
                        break;
                    case 2:
                        intent = new Intent(context, Application.class);
                        break;
                    case 3:
                        intent= new Intent(context, MyApplicantsActivity.class);

                    default:
                        intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("Title", category.getTitle());
                        intent.putExtra("Description", category.getDescription());
                        intent.putExtra("Image", category.getCategoryImage());
                        break;
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        TextView item_description;
        ImageView item_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_description = itemView.findViewById(R.id.item_description);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }
}
