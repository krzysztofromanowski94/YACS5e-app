package com.ptpthingers.grpctasks;

public class GrpcResult {
    boolean ok;
    String status;

    public GrpcResult(boolean ok, String status) {
        this.ok = ok;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public boolean isOk() {
        return this.ok;
    }
}
