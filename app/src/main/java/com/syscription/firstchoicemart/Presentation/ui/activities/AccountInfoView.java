package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.ShippingAddress;
import com.syscription.firstchoicemart.Models.User;
import com.syscription.firstchoicemart.Network.response.ProfileInfoUpdateResponse;
import com.syscription.firstchoicemart.Network.response.ShippingInfoResponse;

import java.util.List;

public interface AccountInfoView {
    void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse);
    void setShippingAddresses(List<ShippingAddress> shippingAddresses);
    void onShippingInfoCreated(ShippingInfoResponse shippingInfoResponse);
    void onShippingInfoDeleted(ShippingInfoResponse shippingInfoResponse);
    void setUpdatedUserInfo(User user);
}
