package kpo.kdz2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDishDTO {

    private Integer id;

    @NotNull
    private Integer quantity;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "75.08")
    private BigDecimal price;

    @NotNull
    private Integer order;

    @NotNull
    private Integer dish;

}
