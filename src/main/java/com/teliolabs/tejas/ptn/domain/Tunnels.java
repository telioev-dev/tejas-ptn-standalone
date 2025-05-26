package com.teliolabs.tejas.ptn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TEJAS_PTN_TUNNEL")
@Slf4j
public class Tunnels {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tejasGponOltSeqGen")
	@SequenceGenerator(name = "tejasGponOltSeqGen", sequenceName = "TEJAS_GPON_OLT_SEQ", allocationSize = 1)
	private Long id;

    
    @Column(name = "TRAIL_ID")
    private String trailID;

    @Column(name = "USER_LABEL")
    private String userLabel;
    
    @Column(name = "CIRCUIT_ID")
    private String circuitID;

    @Column(name = "RATE")
    private String rate;

    @Column(name = "TECHNOLOGY")
    private String technology;

    @Column(name = "SPECIFICATION")
    private String specification;

    @Column(name = "PATH_TYPE")
    private String pathType;
    
    @Column(name = "VENDOR")
    private String vendor;
    
    @Column(name = "TOPOLOGY")
    private String topology;
    
    @Column(name = "A_END_DROP_NODE")
    private String aEndDropNode;

    @Column(name = "Z_END_DROP_NODE")
    private String zEndDropNode;
    
    @Column(name = "A_END_DROP_PORT")
    private String aEndDropPort;

    @Column(name = "Z_END_DROP_PORT")
    private String zEndDropPort;


    @Column(name = "A_END_NODE")
    private String aEndNode;

    @Column(name = "Z_END_NODE")
    private String zEndNode;

    @Column(name = "A_END_PORT")
    private String aEndPort;
    
    @Column(name = "Z_END_PORT")
    private String zEndPort;
    
    @Column(name = "CIRCLE")
    private String circle;
    
    @Column(name = "TOPOLOGY_TYPE")
    private String topologyType;
}