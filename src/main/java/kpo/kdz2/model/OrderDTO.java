package kpo.kdz2.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
public class OrderDTO {

    private Integer id;


    private ArrayList<Integer> orderDishesId;

    @NotNull
    @Size(max = 255)
    private String status;

    private String specialRequest;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Integer user;

}
