package com.teliolabs.tejas.ptn.dto.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teliolabs.tejas.ptn.domain.Tunnels;
import com.teliolabs.tejas.ptn.repository.TunnelRepo;

@Service
public class TunnelService {

    private final TunnelRepo tunnelRepo;

    @Autowired
    public TunnelService(TunnelRepo tunnelRepo) {
        this.tunnelRepo = tunnelRepo;
    }

    @Transactional
    public List<Tunnels> saveTunnelData(List<String[]> tunnelData) {
        if (tunnelData == null) {
            throw new IllegalArgumentException("Tunnel data cannot be null");
        }

        List<Tunnels> tunnels = new ArrayList<>();
        for (String[] row : tunnelData) {
            if (row == null || row.length < 19) {
                // log.warn("Skipping invalid row: {}", Arrays.toString(row));
                continue;
            }

            Tunnels tunnel = new Tunnels();
            tunnel.setTrailID(row[0] != null ? row[0] : "");
            tunnel.setUserLabel(row[1] != null ? row[1] : "");
            tunnel.setCircuitID(row[2] != null ? row[2] : "");
            tunnel.setRate(row[3] != null ? row[3] : "");
            tunnel.setTechnology(row[4] != null ? row[4] : "");
            tunnel.setSpecification(row[5] != null ? row[5] : "");
            tunnel.setPathType(row[6] != null ? row[6] : "");
            tunnel.setVendor(row[7] != null ? row[7] : "");
            tunnel.setTopology(row[8] != null ? row[8] : "");
            tunnel.setAEndDropNode(row[9] != null ? row[9] : "");
            tunnel.setZEndDropNode(row[10] != null ? row[10] : "");
            tunnel.setAEndDropPort(row[11] != null ? row[11] : "");
            tunnel.setZEndDropPort(row[12] != null ? row[12] : "");
            tunnel.setAEndNode(row[13] != null ? row[13] : "");
            tunnel.setZEndNode(row[14] != null ? row[14] : "");
            tunnel.setAEndPort(row[15] != null ? row[15] : "");
            tunnel.setZEndPort(row[16] != null ? row[16] : "");
            tunnel.setCircle(row[17] != null ? row[17] : "");
            tunnel.setTopologyType(row[18] != null ? row[18] : "");

            tunnels.add(tunnel);
        }

        return tunnelRepo.saveAll(tunnels);
    }
}
