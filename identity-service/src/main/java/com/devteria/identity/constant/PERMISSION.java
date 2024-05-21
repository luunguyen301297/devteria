package com.devteria.identity.constant;

public enum PERMISSION {
    APPROVE_POST("APPROVE POST"),
    REJECT_POST("REJECT POST"),
    CREATE_POST("CREATE POST");

    public final String val;

    PERMISSION(String val) {
        this.val = val;
    }
}
