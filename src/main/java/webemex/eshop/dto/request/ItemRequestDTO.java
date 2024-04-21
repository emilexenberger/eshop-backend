package webemex.eshop.dto.request;

import lombok.Data;

@Data
public class ItemRequestDTO {
    private String id;
    private String productName;
    private Integer productCode;
    private Double price;
    private Integer volume;
}
