package task.aisa.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CreateOrderDTO {

    @NotNull
    private Long serviceId;

    @NotNull
    @Future
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date orderDate;
}
