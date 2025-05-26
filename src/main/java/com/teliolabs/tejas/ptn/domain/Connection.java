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
@Table(name = "TEJAS_PTN_TOPOLOGY")
@Slf4j
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tejasPTNSeqGen")
	@SequenceGenerator(name = "tejasPTNSeqGen", sequenceName = "TEJAS_PTN_SEQ", allocationSize = 1)
	private Long id;

    @Column(name = "USER_LABEL")
    private String userLabel;

    @Column(name = "RATE")
    private String rate;

    @Column(name = "TECHNOLOGY")
    private String technology;

    @Column(name = "SPECIFICATION")
    private String specification;

    @Column(name = "VENDOR")
    private String vendor;

    @Column(name = "Z_END_VENDOR")
    private String zEndVendor;

    @Column(name = "A_END_VENDOR")
    private String aEndVendor;

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

    @Column(name = "NATIVE_EMS_NAME")
    private String nativeEmsName;

    @Column(name = "LAST_MODIFIED")
    private String lastModified;
}
