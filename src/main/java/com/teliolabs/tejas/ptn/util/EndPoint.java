package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EndPoint {

    @JsonProperty("nrp-carrier-eth-connectivity-end-point-resource")
    public NrpCarrierEthConnectivityEndPointResource nrpCarrierEthConnectivityEndPointResource;
    @JsonProperty("lifecycle-state")
    public String lifecycleState;
    @JsonProperty("name")
    public ArrayList<Name> name;
    @JsonProperty("service-interface-point")
    public ServiceInterfacePoint serviceInterfacePoint;
    @JsonProperty("layer-protocol-name")
    public String layerProtocolMame;
    @JsonProperty("layer-protocol-qualifier")
    public String layerProtocolQualifier;
    @JsonProperty("connection-end-point")
    public ArrayList<ConnectionEndPoint> connectionEndPoint;
    @JsonProperty("direction")
    public String direction;
    @JsonProperty("additional-information")
    public ArrayList<AdditionalInformation> additionalInformation;
    @JsonProperty("uuid")
    public String uuid;
    @JsonProperty("downstream-bandwidth-profile")
    public DownstreamBandwidthProfile downstreamBandwidthProfile;
}
