package org.example.dao.jdbc;

import org.example.dao.Dao;
import org.example.model.Message;

import java.util.List;

public class MessageJDBCDaoImpl implements Dao <Long, Message>{

    @Override
    public void createTable() {

    }

    @Override
    public void deleteTable() {

    }

    @Override
    public void deleteAllFromTable() {

    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public Message findById(Long id) {
        return null;
    }

    @Override
    public List<Message> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Message entity) {

    }
}
