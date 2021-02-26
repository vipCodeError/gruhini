package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.CartModel;
import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.AccountInfoPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.AccountInfoView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.AccountInfoActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.CartItemListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ViewHolder> {
    private final List<ShippingAddress> shippingAddresses;
    private final LayoutInflater mInflater;
    private final Context context;
    private final ShippingAddressListener shippingAddressListener;
    private final AuthResponse authResponse;

    public Context getContext(){
        return this.context;
    }

    // data is passed into the constructor
    public ShippingAddressAdapter(Context context, List<ShippingAddress> shippingAddresses, AuthResponse authResponse, ShippingAddressListener shippingAddressListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.shippingAddresses = shippingAddresses;
        this.shippingAddressListener = shippingAddressListener;
        this.authResponse = authResponse;
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
        holder.bind(shippingAddresses.get(position));
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
        CheckBox defaultCheck;
        ImageButton deleteBtn;

        ViewHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            postal_code = itemView.findViewById(R.id.postal_code);
            country = itemView.findViewById(R.id.country);
            phone = itemView.findViewById(R.id.phone);
            defaultCheck = itemView.findViewById(R.id.shipping_card_checkbox);
            deleteBtn = itemView.findViewById(R.id.delete_ship_addr);
        }

        public void bind(ShippingAddress shippingAddress) {
            address.setText(shippingAddress.getAddress());
            city.setText(shippingAddress.getCity());
            postal_code.setText(shippingAddress.getPostalCode());
            country.setText(shippingAddress.getCountry());
            phone.setText(shippingAddress.getPhone());
            defaultCheck.setVisibility(View.GONE);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDBuilder = new AlertDialog.Builder(context);
                    alertDBuilder.setTitle("Delete");
                    alertDBuilder.setMessage("Want To Delete This Shpping Address ?");
                    alertDBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (AccountInfoView) context).deleteShippingAddress(shippingAddress.getId(), authResponse.getAccessToken());
                        }
                    });
                    alertDBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDBuilder.show();
                }
            });

        }
    }
}

