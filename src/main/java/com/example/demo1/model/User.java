package com.example.demo1.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static com.example.demo1.model.StatusOfEnable.AWAY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class User{
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Length(min = 2)
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    private String phoneNumber;

    @Column(name = "enabled")
    private StatusOfEnable enabled;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @Column(name = "online_time")
    private Date onlineTime;

    public User(Integer id, String name, String email,String password, String phoneNumber, StatusOfEnable enabled, Date registered, Date onlineTime){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.registered = registered;
        this.onlineTime = onlineTime;
    }

    public User(Integer id, String name, String email,String password, String phoneNumber){
        this(id,name,email,password,phoneNumber,StatusOfEnable.ONLINE,new Date(),new Date());
    }

    public User(String name, String email,String password, String phoneNumber){
        this.id = null;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.enabled = StatusOfEnable.ONLINE;
        this.registered = new Date();
        this.onlineTime = new Date();

    }
    public User(String name, String email,String password, String phoneNumber, StatusOfEnable enabled){
        this(null,name,email,password,phoneNumber,enabled,new Date(),new Date());
    }


    public User(User u){
        this(u.getId(),u.getName(),u.getEmail(),u.getPassword(),u.getPhoneNumber(),u.getEnabled(),u.getRegistered(), u.getOnlineTime());
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", enabled=" + enabled + '\''+
                ", registered=" + registered +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getName().equals(user.getName()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getPhoneNumber().equals(user.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPassword(), getPhoneNumber());
    }
}
