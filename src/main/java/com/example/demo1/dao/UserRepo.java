package com.example.demo1.dao;


import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    List<User> findByEnabledAndStatusTimestampAfter (StatusOfEnable enabled, Timestamp statusTimestamp);
    List<User> findByEnabled (StatusOfEnable enabled);
    List<User> findByStatusTimestampAfter (Timestamp statusTimestamp);

}
