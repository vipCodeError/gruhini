package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressSelectListener;
import com.activeitzone.activeecommercecms.R;

import java.util.List;

public class ShippingAddressSelectAdapter extends RecyclerView.Adapter<ShippingAddressSelectAdapter.ViewHolder> {
    private final List<ShippingAddress> shippingAddresses;
    private final LayoutInflater mInflater;
    private final Context context;
    private final ShippingAddressSelectListener shippingAddressSelectListener;
    private int row_index = -1;

    public Context getContext(){
        return this.context;
    }

    // data is passed into the constructor
    public ShippingAddressSelectAdapter(Context context, List<ShippingAddress> shippingAddresses, ShippingAddressSelectListener shippingAddressSelectListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.shippingAddresses = shippingAddresses;
        this.shippingAddressSelectListener = shippingAddressSelectListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shipping_address_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(shippingAddresses.get(position), position);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return shippingAddresses.size();
    }

    // convenience method for getting data at click position
    public ShippingAddress getItem(int id) {
        return shippingAddresses.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView city;
        TextView postal_code;
        TextView country;
        TextView phone;
        CheckBox selectedAddCheckBo;
        ImageButton closeBtn;

        ViewHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            postal_code = itemView.findViewById(R.id.postal_code);
            country = itemView.findViewById(R.id.country);
            phone = itemView.findViewById(R.id.phone);
            selectedAddCheckBo = itemView.findViewById(R.id.shipping_card_checkbox);
            closeBtn = itemView.findViewById(R.id.delete_ship_addr);

        }

        public void bind(ShippingAddress shippingAddress, int position) {
            address.setText(shippingAddress.getAddress());
            city.setText(shippingAddress.getCity());
            postal_code.setText(shippingAddress.getPostalCode());
            country.setText(shippingAddress.getCountry());
            phone.setText(shippingAddress.getPhone());
            closeBtn.setVisibility(View.GONE);
            

            selectedAddCheckBo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        row_index = position;
                        notifyDataSetChanged();
                        shippingAddressSelectListener.onShippingAddressItemClick(shippingAddress);
                    }else {
                        Toast.makeText(context, "Select atleast one Address", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}

