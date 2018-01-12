package com.wjs.study.memSize;

import java.io.Serializable;

/**
 * Created by 王金绍 on 2017/6/13 16:50.
 */
public class RpcLog implements Serializable {

    public String srcAppId; // required
    public String aimAppId; // optional
    public String aimService; // optional
    public String aimMethod; // optional
    public String aimVersion; // optional
    public String srcAddress; // optional
    public String aimAddress; // optional
    public long reqLength; // optional
    public long respLength; // optional
    public boolean clientSide; // optional
    public String traceId; // optional
    public String spanId; // optional
    public String parentSpanId; // optional
    public long startTime; // required
    public long endTime; // required
    public int rs; // optional
    public String params; // optional
    public String result; // optional

    public String getSrcAppId() {
        return srcAppId;
    }

    public void setSrcAppId(String srcAppId) {
        this.srcAppId = srcAppId;
    }

    public String getAimAppId() {
        return aimAppId;
    }

    public void setAimAppId(String aimAppId) {
        this.aimAppId = aimAppId;
    }

    public String getAimService() {
        return aimService;
    }

    public void setAimService(String aimService) {
        this.aimService = aimService;
    }

    public String getAimMethod() {
        return aimMethod;
    }

    public void setAimMethod(String aimMethod) {
        this.aimMethod = aimMethod;
    }

    public String getAimVersion() {
        return aimVersion;
    }

    public void setAimVersion(String aimVersion) {
        this.aimVersion = aimVersion;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public String getAimAddress() {
        return aimAddress;
    }

    public void setAimAddress(String aimAddress) {
        this.aimAddress = aimAddress;
    }

    public long getReqLength() {
        return reqLength;
    }

    public void setReqLength(long reqLength) {
        this.reqLength = reqLength;
    }

    public long getRespLength() {
        return respLength;
    }

    public void setRespLength(long respLength) {
        this.respLength = respLength;
    }

    public boolean isClientSide() {
        return clientSide;
    }

    public void setClientSide(boolean clientSide) {
        this.clientSide = clientSide;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
