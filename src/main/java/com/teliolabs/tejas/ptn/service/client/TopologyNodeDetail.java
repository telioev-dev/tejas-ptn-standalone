package com.teliolabs.tejas.ptn.service.client;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teliolabs.tejas.ptn.util.AdditionalInformation;
import com.teliolabs.tejas.ptn.util.NodeEdgePoint;
import com.teliolabs.tejas.ptn.util.TapiCommonNameAndValue;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopologyNodeDetail {
	
	@JsonIgnore
	 public String operationalState;
	
	 @JsonProperty("lifecycle-state")
	    public String lifecycleState;
	    @JsonProperty("administrative-state")
	    public String administrativeState;
	    @JsonProperty("name")
	    public ArrayList<TapiCommonNameAndValue> name;
	    @JsonProperty("uuid")
	    public String uuid;
	    @JsonProperty("additional-information")
	    public ArrayList<AdditionalInformation> additionalIinformation;
	    @JsonProperty("node-edge-point")
	    public ArrayList<NodeEdgePoint> nodeEdgePoint;

	    
	    
	    
	    
//	@JsonProperty("operational-state")
//	private String opertationalState;
//	
//	@JsonProperty("lifecycle-state")
//	private String lifeCycleState;
//	
//	@JsonProperty("administrative-state")
//	private String administrativeState;
//	
//	@JsonProperty("name")
//	private List<TopologyCommonNameAndValue> name;
//	
//	@JsonProperty("additional-information")
//	private List<TopologyAdditionalInformation> additionalInformation;
//	
//	@JsonProperty("node-edge-point")
//	private List<TopologyNodeEdgePoint> nodeEdgePoint;
//	
	
	
	
}