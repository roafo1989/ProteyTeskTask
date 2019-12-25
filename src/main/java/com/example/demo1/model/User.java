package com.example.demo1.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class User{
    @Id
//    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue/*(strategy = GenerationType.SEQUENCE, generator = "global_seq")*/
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

    @Column(name = "enabled", nullable = false)
    private StatusOfEnable enabled;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @Column(name = "status_timestamp")
    private Timestamp statusTimestamp;

    public User(Integer id, String name, String email,String password, String phoneNumber, Timestamp statusTimestamp){
        this(id,name,email,password,phoneNumber,StatusOfEnable.ONLINE,new Date(),statusTimestamp);
    }

    public User(User u){
        this(u.getId(),u.getName(),u.getEmail(),u.getPassword(),u.getPhoneNumber(),u.getEnabled(),u.getRegistered(),u.getStatusTimestamp());
    }

    public User(Integer id, String name, String email, String password) {
        this(id,name,email,password,null, StatusOfEnable.ONLINE,new Date(), new Timestamp((new Date()).getTime()));
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
                ", registered=" + registered + '\''+
                ", statusTimestamp=" + statusTimestamp +
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
