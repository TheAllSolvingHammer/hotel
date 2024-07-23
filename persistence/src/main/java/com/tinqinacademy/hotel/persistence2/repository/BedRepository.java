package com.tinqinacademy.hotel.persistence2.repository;

import com.tinqinacademy.hotel.persistence2.entities.BedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class BedRepository implements BaseRepository<BedEntity, UUID>{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void save(BedEntity entity) {
        String sql="Insert into beds(id,type,capacity) values(?,?,?)";
        jdbcTemplate.update(sql,entity.getId(),entity.getType(),entity.getCapacity());
    }

    @Override
    public BedEntity findById(UUID id) {
        String sql = "select (*) from beds where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BedEntity.class), id);
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM beds WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<BedEntity> findAll() {
        String sql="select * from beds";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<BedEntity>(BedEntity.class));
    }

    @Override
    public Long count() {
        String sql = "select count(*) from beds";
       return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
