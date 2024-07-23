package com.tinqinacademy.hotel.persistence2.repository;

import com.tinqinacademy.hotel.persistence2.entities.RoomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class RoomRepository implements BaseRepository<RoomEntity, UUID> {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public RoomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void save(RoomEntity entity) {
        String sql="insert into rooms(id,floor,bathroom_type,price,room_number) values(?,?,?,?,?)";
        jdbcTemplate.update(sql
                ,entity.getId()
                ,entity.getFloor()
                ,entity.getBathRoom()
                ,entity.getPrice()
                ,entity.getRoomNumber());
    }

    @Override
    public RoomEntity findById(UUID id) {
        String sql = "select * from rooms where id=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RoomEntity.class), id);
    }

    @Override
    public void delete(UUID id) {
        String sql = "delete from rooms where id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<RoomEntity> findAll() {
        String sql = "select * from rooms";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<RoomEntity>(RoomEntity.class));
    }

    @Override
    public Long count() {
        String sql = "select count(*) from rooms";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
