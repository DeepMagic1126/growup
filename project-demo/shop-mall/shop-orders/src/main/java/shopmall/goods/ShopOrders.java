package shopmall.goods;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopOrders {

    private Integer id;

    private Integer gid;

    private Integer purchaseAmount;

    private LocalDateTime createTime;
}
