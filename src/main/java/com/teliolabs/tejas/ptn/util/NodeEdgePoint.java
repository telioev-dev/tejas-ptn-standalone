package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeEdgePoint {
	@JsonProperty("uuid")
	public String uuid;
	@JsonProperty("additional-information")
	public ArrayList<AdditionalInformation> additionalInformation;
	@JsonProperty("lifecycle-state")
	public String lifecycleState;
	@JsonProperty("administrative-state")
	public String administrativeState;
	@JsonProperty("name")
	public ArrayList<TapiCommonNameAndValue> name;
	@JsonProperty("layer-protocol-name")
	public String layerProtocolName;
	@JsonProperty("node-uuid")
	public String nodeUuid;
	@JsonProperty("topology-uuid")
	public String topologyUuid;
	@JsonProperty("node-edge-point-uuid")
	public String nodeEdgePointUuid;
}
