package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erp {
    @JsonProperty("additional-info")
    public ArrayList<AdditionalInformation> additionalInfo;

    @JsonProperty("erp-ringlet")
    public ArrayList<ErpRinglet> erpRinglet;

    @JsonProperty("erp-handoff-port-type")
    public String erpHandoffPortType;

    @JsonProperty("erp-type")
    public String erpType;

    @JsonProperty("uuid")
    public String uuid;

    @JsonProperty("erp-traffic-type")
    public String erpTrafficType;

    @JsonProperty("lifecycle-state")
    public String lifecycleState;

    @JsonProperty("erp-id")
    public int erpId;

    @JsonProperty("links")
    public ArrayList<Link> links;

    @JsonProperty("raps-priority")
    public int rapsPriority;

    @JsonProperty("erp-name")
    public String erpName;
}
