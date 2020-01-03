package com.example.demo1.util;


import com.example.demo1.model.StatusOfEnable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {

    private Integer id;
    private StatusOfEnable oldStatus;
    private StatusOfEnable currentStatus;

    @Override
    public String toString() {
        return "StatusResponse{" +
                "id='" + id + '\'' +
                ", oldStatus='" + oldStatus + '\'' +
                ", currentStatus='" + currentStatus +
                '}';
    }
}
