package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Root {
    @JsonProperty("uuid")
    public String uuid;
    @JsonProperty("node-edge-point")
    public ArrayList<NodeEdgePoint> nodeEdgePoint;
    @JsonProperty("remote-unit")
    public RemoteUnit remoteUnit;
    @JsonProperty("additional-information")
    public ArrayList<AdditionalInformation> additionalInformation;
    @JsonProperty("operational-state")
    public String operationalState;
    @JsonProperty("lifecycle-state")
    public String lifecycleState;
    @JsonProperty("administrative-state")
    public String administrativeState;
    @JsonProperty("name")
    public ArrayList<Name> name;
    @JsonProperty("direction")
    public String direction;
    @JsonProperty("erp")
    public Erp erp;
    @JsonProperty("connectivity-service") 
    public ConnectivityService connectivityService;
}
