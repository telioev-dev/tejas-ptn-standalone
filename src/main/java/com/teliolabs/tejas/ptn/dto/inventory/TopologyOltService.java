package com.teliolabs.tejas.ptn.dto.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teliolabs.tejas.ptn.domain.TopologyOlt;
import com.teliolabs.tejas.ptn.repository.TopologyOltRepo;
import com.teliolabs.tejas.ptn.repository.TopologyRepo;

@Service
public class TopologyOltService {

    private final TopologyOltRepo topologyRepo;

    @Autowired
    public TopologyOltService(TopologyOltRepo topologyRepo) {
        this.topologyRepo = topologyRepo;
    }

    @Transactional
    public List<TopologyOlt> saveTopologyData(List<String[]> topologyData) {
        if (topologyData == null) {
            throw new IllegalArgumentException("Topology data cannot be null");
        }

        List<TopologyOlt> connections = new ArrayList<>();
        for (String[] row : topologyData) {
            if (row == null || row.length < 12) {
                // log.warn("Skipping invalid row: {}", Arrays.toString(row));
                continue;
            }

            TopologyOlt connection = new TopologyOlt();
            connection.setUserLabel(row[0]);
            connection.setRate(row[1]);
            connection.setTechnology(row[2]);
            connection.setSpecification(row[3]);
            connection.setVendor(row[4]);
            connection.setZEndVendor(row[5]);
            connection.setAEndVendor(row[6]);
            connection.setAEndNode(row[7]);
            connection.setZEndNode(row[8]);
            connection.setAEndPort(row[9]);
            connection.setZEndPort(row[10]);
            connection.setCircle(row[11]);

            connections.add(connection);
        }
        return topologyRepo.saveAll(connections);
    }
}
