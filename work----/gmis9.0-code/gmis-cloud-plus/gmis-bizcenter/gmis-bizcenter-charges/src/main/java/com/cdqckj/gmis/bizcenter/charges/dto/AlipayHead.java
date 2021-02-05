package com.cdqckj.gmis.bizcenter.charges.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class AlipayHead {
    private String version;
    private final String source = "ALIPAY";
    private final String desIfno = "WTGDSHENZHEN";
    private String servCode = "";
    private String msgId = "";
    private String msgTime;
    private String extend = "";
}
