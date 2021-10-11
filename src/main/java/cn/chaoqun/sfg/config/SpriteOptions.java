package cn.chaoqun.sfg.config;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chaoqun
 * @date 2021/10/4 12:01
 */
@Data
@Builder
public class SpriteOptions {

    @Builder.Default
    private int pipWeight = 360;
    @Builder.Default
    private int pipHeight = 204;

    private int hGap;
    private int vGap;

    @Builder.Default
    private int primaryAxisMaxN = 3;
    @Builder.Default
    private LayoutDirection layoutDirection = LayoutDirection.ROW_FIRST;

    public enum LayoutDirection{
        /**
         * 布局方向
         */
        ROW_FIRST, COL_FIRST
    }

}
