package kpo.kdz2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SessionDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String sessionToken;

    @NotNull
    private LocalDateTime expiresAt;

    private Integer user;

}
