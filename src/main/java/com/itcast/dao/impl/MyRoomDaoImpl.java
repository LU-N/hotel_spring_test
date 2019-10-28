package com.itcast.dao.impl;

import com.itcast.dao.MyRoomDao;
import com.itcast.domain.MyRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author JinLu
 * @date 2019/10/27
 */
@Repository
public class MyRoomDaoImpl implements MyRoomDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void add(MyRoom myRoom) {
        String sql = "insert into myroom values(null, ? , ?)";
        template.update(sql, myRoom.getName(), myRoom.getImage());

        String idSql = "select last_insert_id()";
        Integer id = template.queryForObject(idSql, Integer.class);
        myRoom.setId(id);
    }

    @Override
    public void update(MyRoom myRoom) {
        String sql = "update myroom set name=?, image=? where id=?";
        template.update(sql, myRoom.getName(), myRoom.getImage(), myRoom.getId());
    }

    @Override
    public MyRoom findById(int id) {
        try {
            String sql = "select * from myroom where id=?";
            MyRoom myRoom = template.queryForObject(sql, new BeanPropertyRowMapper<>(MyRoom.class), id);
            return myRoom;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
