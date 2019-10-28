package com.itcast.dao;

import com.itcast.domain.MyRoom;

/**
 * @author JinLu
 * @date 2019/10/27
 */
public interface MyRoomDao {
    /**
     * 新增房间
     *
     * @param myRoom
     */
    void add(MyRoom myRoom);

    /**
     * 修改房间
     *
     * @param myRoom
     */
    void update(MyRoom myRoom);

    /**
     * 按id查找房间
     *
     * @param id
     */
    MyRoom findById(int id);
}
