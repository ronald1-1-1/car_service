package task.aisa.dto.order;

import lombok.Data;
import task.aisa.dto.service.ServiceDTO;

import java.util.Date;

@Data
public class OrderDTO {

    private Long id;
    private ServiceDTO service;
    private Date date;

}
