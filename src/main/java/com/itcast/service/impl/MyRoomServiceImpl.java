package com.itcast.service.impl;

import com.itcast.dao.MyRoomDao;
import com.itcast.domain.MyRoom;
import com.itcast.service.MyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinLu
 * @date 2019/10/28
 */
@Service
@Transactional
public class MyRoomServiceImpl implements MyRoomService {

    @Autowired
    private MyRoomDao myRoomDao;

    @Override
    public void add(MyRoom myRoom) {
        myRoomDao.add(myRoom);
    }

    @Override
    public void update(MyRoom myRoom) {
        myRoomDao.update(myRoom);
    }

    @Override
    public MyRoom findById(int id) {
        return myRoomDao.findById(id);
    }
}
