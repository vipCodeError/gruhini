package com.activeitzone.activeecommercecms.Models;

import com.google.gson.annotations.SerializedName;

public class PreferCatModel {
    @SerializedName("id")
    public String id;
    @SerializedName("cat_id")
    public String catId;
    @SerializedName("sub_id")
    public String subId;
    @SerializedName("which_position")
    public int whichPosition;
    @SerializedName("is_sub_shown")
    public String subShown;
}
