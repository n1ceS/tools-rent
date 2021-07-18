package pl.marczuk.toolsrent.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    public ItemDto(@NotEmpty String name, @NotEmpty String description, @NotNull BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
