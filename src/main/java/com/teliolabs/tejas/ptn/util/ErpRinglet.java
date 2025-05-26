package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErpRinglet {
    @JsonProperty("erp-ringlet-reversion-mode")
    public String erpRingletReversionMode;

    @JsonProperty("control-vid")
    public int controlVid;

    @JsonProperty("erp-ringlet-name")
    public String erpRingletName;

    @JsonProperty("open-vid")
    public String openVid;

    @JsonProperty("lifecycle-state")
    public String lifecycleState;

    @JsonProperty("additional-info")
    public ArrayList<AdditionalInformation> additionalInfo;

    @JsonProperty("ringlet-owner")
    public RingletOwner ringletOwner;

    @JsonProperty("uuid")
    public String uuid;

    @JsonProperty("erp-ringlet-protection-state")
    public String erpRingletProtectionState;

    @JsonProperty("data-vid")
    public String dataVid;
}
