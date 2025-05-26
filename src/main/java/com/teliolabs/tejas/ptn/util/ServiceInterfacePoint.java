package com.teliolabs.tejas.ptn.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceInterfacePoint {
    @JsonProperty("service-interface-point-uuid")
    public String serviceInterfacePointUuid;
}
