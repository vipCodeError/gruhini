package com.syscription.firstchoicemart.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syscription.firstchoicemart.Models.Category;
import com.syscription.firstchoicemart.Presentation.ui.listeners.CategoryClickListener;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Utils.AppConfig;

import java.util.List;

public class TopSecondCategoryAdapter extends RecyclerView.Adapter<TopSecondCategoryAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> mCategories;
    private final LayoutInflater mInflater;
    private final CategoryClickListener mClickListener;

    // data is passed into the constructor
    public TopSecondCategoryAdapter(Context context, List<Category> categories, CategoryClickListener listener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mCategories = categories;
        this.mClickListener = listener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public TopSecondCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.sec_top_category_list_item, parent, false);
        return new TopSecondCategoryAdapter.ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull TopSecondCategoryAdapter.ViewHolder holder, int position) {
        holder.bind(mCategories.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_icon);
            textView = itemView.findViewById(R.id.category_name);
        }

        public void bind(final Category category) {
            Glide.with(context).load(AppConfig.ASSET_URL + category.getIcon()).into(imageView);

            textView.setText(category.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onCategoryItemClick(category);
                }
            });
        }
    }
}
