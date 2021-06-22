package uz.pdp.appwarehouse.payload;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class InputProductDto {
    private Integer productId;

    private Double amount;

    private Double price;

    private Date expireDate;

    private Integer inputId;

}
