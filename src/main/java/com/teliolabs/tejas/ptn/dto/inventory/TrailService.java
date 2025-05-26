package com.teliolabs.tejas.ptn.dto.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teliolabs.tejas.ptn.domain.Trails;
import com.teliolabs.tejas.ptn.repository.TrailRepo;


@Service
public class TrailService {

    private final TrailRepo trailRepo;

    @Autowired
    public TrailService(TrailRepo trailRepo) {
        this.trailRepo = trailRepo;
    }

    @Transactional
    public List<Trails> saveTrailData(List<String[]> trailData) {
        if (trailData == null) {
            throw new IllegalArgumentException("Trail data cannot be null");
        }

        List<Trails> trails = new ArrayList<>();
        for (String[] row : trailData) {
            if (row == null || row.length < 19) {
                // log.warn("Skipping invalid row: {}", Arrays.toString(row));
                continue;
            }

            Trails trail = new Trails();
            trail.setTrailID(row[0] != null ? row[0] : "");
            trail.setUserLabel(row[1] != null ? row[1] : "");
            trail.setCircuitID(row[2] != null ? row[2] : "");
            trail.setRate(row[3] != null ? row[3] : "");
            trail.setTechnology(row[4] != null ? row[4] : "");
            trail.setSpecification(row[5] != null ? row[5] : "");
            trail.setPathType(row[6] != null ? row[6] : "");
            trail.setVendor(row[7] != null ? row[7] : "");
            trail.setTopology(row[8] != null ? row[8] : "");
            trail.setAEndDropNode(row[9] != null ? row[9] : "");
            trail.setZEndDropNode(row[10] != null ? row[10] : "");
            trail.setAEndDropPort(row[11] != null ? row[11] : "");
            trail.setZEndDropPort(row[12] != null ? row[12] : "");
            trail.setAEndNode(row[13] != null ? row[13] : "");
            trail.setZEndNode(row[14] != null ? row[14] : "");
            trail.setAEndPort(row[15] != null ? row[15] : "");
            trail.setZEndPort(row[16] != null ? row[16] : "");
            trail.setCircle(row[17] != null ? row[17] : "");
            trail.setTopologyType(row[18] != null ? row[18] : "");

            trails.add(trail);
        }

        return trailRepo.saveAll(trails);
    }

}
