package com.example.dronedeliveryapp.delivery.repository;

import com.example.dronedeliveryapp.delivery.model.Delivery;
import com.example.dronedeliveryapp.delivery.model.DeliveryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query(value = """
                        SELECT
                            d.tracking_id AS trackingId,
                            dr.serial_number AS serialNumber,
                            dr.drone_state AS droneState,
                            dr.battery_capacity AS batteryCapacity,
                            COUNT(*) OVER() AS totalCount,
                            med.image,
                            med.medication_name AS medicationName,
                            med.code,
                            med.weight_limit AS weightLimit,
                            med.weight_unit AS weightUnit
                        FROM delivery d
                        INNER JOIN drone dr on d.drone_id = dr.drone_id
                            AND dr.drone_state = 'LOADED'
                            AND  dr.serial_number= :droneSerialNumber
                        INNER JOIN medication med on med.medication_id = d.medication_id
                         ORDER BY dr.updated_at DESC
                        OFFSET  (:pageNumber * :pageSize) ROWS FETCH NEXT :pageSize ROWS ONLY;
            """, nativeQuery = true)
    List<DeliveryDTO> retrieveLoadedMedicationForAGivenDrone(@Param("droneSerialNumber") String droneSerialNumber, @Param("pageNumber") int pageNumber, @Param(("pageSize")) int pageSize);
}
