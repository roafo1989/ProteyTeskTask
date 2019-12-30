package com.example.demo1.dao;


import com.example.demo1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;


@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Integer> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE User u SET u.enabled=1 WHERE u.enabled=0 AND u.onlineTime<=:onlineTime")
    void updateStatus(@Param("onlineTime") Date onlineTime);

}
