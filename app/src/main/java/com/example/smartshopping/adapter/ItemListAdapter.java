package com.example.smartshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartshopping.R;
import com.example.smartshopping.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private Context context;
    private List<ItemModel> itemList;
    private ItemClickListener itemClickListener;
    private boolean gridOption;

    public ItemListAdapter(Context context, List<ItemModel> itemList,ItemClickListener itemClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.itemClickListener = itemClickListener;
        gridOption = true;
    }
    public void setItemList(List<ItemModel> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(gridOption ? R.layout.item_grid_layout : R.layout.item_lineary_view,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ItemViewHolder holder, int position) {
        holder.tvTitle.setText(this.itemList.get(position).getTitle().toString());
//        Glide.with(context)
//                .load(this.itemList.get(position).getImageUrl())
//                .apply(RequestOptions.centerCropTransform())
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(holder.imageView);
        holder.imageView.setImageResource(this.itemList.get(position).getD());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.itemList != null){
            return this.itemList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView imageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(ItemModel model);
    }

    public void setGridOption(boolean gridOption) {
        this.gridOption = gridOption;
    }

    public boolean isGridOption() {
        return gridOption;
    }

    public void clearData(){
        if(itemList == null) {
            List<ItemModel> temp = new ArrayList<>();
            itemList = temp;
        }
        itemList.clear();
        notifyDataSetChanged();
    }
}
