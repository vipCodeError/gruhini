package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.OrderDetail;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.PurchaseHistoryDetailsActivity;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.github.vipulasri.timelineview.TimelineView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private final List<OrderDetail> orderDetails;
    private final LayoutInflater mInflater;
    private final Context context;
    private TimeLineAdapter timelineAdapter;
    public List<String> orderTrackTitle = Arrays.asList("Order", "Processing", "Out of Delivery", "Delivered");

    // data is passed into the constructor
    public OrderDetailsAdapter(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.orderDetails = orderDetails;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_details_item, parent, false);
        return new ViewHolder(view, viewType);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(orderDetails.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    // convenience method for getting data at click position
    public OrderDetail getItem(int id) {
        return orderDetails.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_variant, product_qty, product_price, delivery_status;
      //  private TimelineView pendingOrderTimeline, proccessedOrderTimeLine, outOfdeliveryOrderTimeLine, deliveredOrderTimeLine;
        private final RecyclerView mRecyclerView;

        ViewHolder(View itemView, int viewType) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_variant = itemView.findViewById(R.id.product_variant);
            product_qty = itemView.findViewById(R.id.product_qty);
            product_price = itemView.findViewById(R.id.product_price);
            delivery_status = itemView.findViewById(R.id.delivery_status);
            mRecyclerView =  itemView.findViewById(R.id.timeLineRecycler);
            timelineAdapter = new TimeLineAdapter(context, orderTrackTitle);
        }

        public void bind(OrderDetail orderDetail) {
            product_name.setText(orderDetail.getProduct());
            product_variant.setText(orderDetail.getVariation());
            product_qty.setText("Qty : " + orderDetail.getQuantity().toString());
            product_price.setText(AppConfig.convertPrice(context, orderDetail.getPrice()));

            mRecyclerView.setAdapter(timelineAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            timelineAdapter.setCurrentStatus(orderDetail.getDeliveryStatus());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());

    }
}

