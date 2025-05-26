package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectivityService {
    @JsonProperty("operational-state")
    public String operationalState;
    @JsonProperty("lifecycle-state")
    public String lifecycleState;
    @JsonProperty("administrative-state")
    public String administrativeState;
    @JsonProperty("name")
    public ArrayList<Name> name;
    @JsonProperty("uuid")
    public String uuid;
    @JsonProperty("additional-information")
    public ArrayList<AdditionalInformation> additionalInformation;

    @JsonProperty("end-point")
    public ArrayList<EndPoint> endPoint;

    @JsonProperty("layer-protocol-name")
    public String layerProtocolName;

    @JsonProperty("connection")
    public ArrayList<Connection> connection;
    @JsonProperty("connectivity-constraint")
    public ConnectivityConstraint connectivityConstraint;

    @JsonProperty("direction")
    public String direction;
}
