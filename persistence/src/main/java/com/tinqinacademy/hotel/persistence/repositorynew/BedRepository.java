package com.tinqinacademy.hotel.persistence.repositorynew;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.BedTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BedRepository extends JpaRepository<BedEntity, UUID> {
    String query= """
            SELECT DISTINCT b.type
            from beds b;
            """;
    @Query(value = query,nativeQuery = true)
    List<String> findAllTypes();

    String FIND_ID_BY_TYPE_QUERY = """
            SELECT b.bed_id,b.capacity,b.type
            FROM beds b
            WHERE b.type = :type;
            """;
    @Query(value = FIND_ID_BY_TYPE_QUERY, nativeQuery = true)
    BedEntity findEntityByType(@Param("type") String type);

}
