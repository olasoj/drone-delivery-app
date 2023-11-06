package com.example.dronedeliveryapp.drone.repository;

import com.example.dronedeliveryapp.drone.model.Drone;
import com.example.dronedeliveryapp.drone.model.DroneDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query(value = """
                        
                SELECT
                    dr.*
                FROM drone dr
                WHERE dr.serial_number = :serialNumber
                LIMIT 1;
            """, nativeQuery = true)
    Optional<Drone> retrieveBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query(value = """
                SELECT
                    dr.*
            FROM drone dr
            WHERE dr.drone_id <= (:pageSize * :pageNumber)
            ORDER BY dr.created_at DESC
            LIMIT (:pageSize);
            """, nativeQuery = true)
    List<Drone> retrieveAllDrone(@Param("pageNumber") long pageNumber, @Param(("pageSize")) long pageSize);

    @Query(value = """    
                SELECT
                    dr.drone_id AS droneId,
                    dr.created_at AS createdAt,
                    dr.updated_at AS updatedAt,
                    dr.serial_number AS serialNumber,
                    dr.weight_unit AS weightUnit,
                    COUNT(*) OVER() AS totalCount,
                    dr.weight_limit AS weightLimit,
                    dr.model AS model,
                    dr.drone_state AS droneState,
                    dr.battery_capacity AS batteryCapacity
            FROM drone dr
            WHERE dr.drone_state = 'IDLE'
            ORDER BY dr.created_at DESC
            OFFSET  (:pageNumber * :pageSize) ROWS FETCH NEXT :pageSize ROWS ONLY;
            """, nativeQuery = true)
    List<DroneDTO> retrieveAllIdleDrone(@Param("pageNumber") int pageNumber, @Param(("pageSize")) int pageSize);

    @Query(value = """
                        
                SELECT
                    dr.battery_capacity
                FROM drone dr
                WHERE dr.serial_number = :serialNumber
                LIMIT 1;
            """, nativeQuery = true)
    BigDecimal retrieveBatteryLevelDTOBySerialNumber(@Param("serialNumber") String serialNumber);

}
