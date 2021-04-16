package com.syscription.firstchoicemart.Models;

import com.google.gson.annotations.SerializedName;

public class PreferCartSubModel {
    @SerializedName("id")
    String id;
    @SerializedName("cat_id")
    String catId;
    @SerializedName("sub_id")
    String subId;
    @SerializedName("which_position")
    String whichPosition;
    @SerializedName("is_sub_shown")
    String subShown;
}
