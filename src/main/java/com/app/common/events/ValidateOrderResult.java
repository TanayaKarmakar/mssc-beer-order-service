package com.app.common.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author t0k02w6 on 05/06/21
 * @project mssc-beer-service
 */
@Data
@NoArgsConstructor
public class ValidateOrderResult {
    private UUID orderId;
    private Boolean isValid;
}
