package com.teliolabs.tejas.ptn.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionEndPoint {
    @JsonProperty("topology-uuid")
    public String topologyUuid;
    @JsonProperty("node-uuid")
    public String nodeUuid;
    @JsonProperty("node-edge-point-uuid")
    public String nodeEdgePointUuid;
    @JsonProperty("connection-end-point-uuid")
    public String connectionEndPointUuid;
}
