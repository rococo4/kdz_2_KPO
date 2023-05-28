package kpo.kdz2.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
public class UserDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String passwordHash;

    @NotNull
    @Size(max = 255)
    private String role;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
