package com.teliolabs.tejas.ptn.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteUnit{
	@JsonProperty("circuit-packs")
    public ArrayList<CircuitPack> circuitPacks;
	@JsonProperty("ont")
    public Ont ont;
    @JsonProperty("node-edge-point")
    public ArrayList<NodeEdgePoint> nodeEdgePoint;
}
