package com.syscription.firstchoicemart.Presentation.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syscription.firstchoicemart.Models.OrderDetail;
import com.syscription.firstchoicemart.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyViewHolder>{

    Context context;
    List<String> orderTrackTimeLineTitleList;
    List<OrderDetail> orderDetails;
    String currentStatus = "";

    public TimeLineAdapter(Context context, List<String> orderTrackTimeLineTitleList) {
        this.context = context;
        this.orderTrackTimeLineTitleList = orderTrackTimeLineTitleList;
    }

    @NonNull
    @Override
    public TimeLineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_line_layout, parent, false);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineAdapter.MyViewHolder holder, int position) {
        holder.timeLineTitle.setText(orderTrackTimeLineTitleList.get(position));

        holder.timeLineView.setLineStyle(TimelineView.LineStyle.DASHED);
            if (getCurrentStatus().equals("pending")){
                if(position == 0){
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_green_dot));
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }else {
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_dot));
                }
            }else if(getCurrentStatus().equals("on_review")){
                if(position == 1 || position == 0){
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_green_dot));

                }else {
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_dot));
                }
                if (position == 0){
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }
                if (position == 1) {
                    holder.timeLineView.setStartLineColor(Color.GREEN, getItemViewType(position));
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }
            }else if(getCurrentStatus().equals("on_deliver")){
                if(position == 2 || position == 1 || position == 0){
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_green_dot));
                }else {
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_dot));
                }
                if (position == 0){
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }
                if (position == 1 || position == 2) {
                    holder.timeLineView.setStartLineColor(Color.GREEN, getItemViewType(position));
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }
            }else if(getCurrentStatus().equals("delivered")){
                if(position == 3 || position == 2 || position == 1 || position == 0){
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_green_dot));
                }else {
                    holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.marker_dot));
                }
                if (position == 0){
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }
                if (position == 1 || position == 2 || position == 3) {
                    holder.timeLineView.setStartLineColor(Color.GREEN, getItemViewType(position));
                    holder.timeLineView.setEndLineColor(Color.GREEN, getItemViewType(position));
                }
            }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return orderTrackTimeLineTitleList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView timeLineTitle;
        TimelineView timeLineView;

        public MyViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            timeLineTitle = itemView.findViewById(R.id.timeLineTitle);
            timeLineView = itemView.findViewById(R.id.timeLine);
            timeLineView.initLine(viewType);
        }
    }

    public void setCurrentStatus(String status){
        currentStatus = status;
    }

    public String getCurrentStatus(){
       return currentStatus;
    }
}
