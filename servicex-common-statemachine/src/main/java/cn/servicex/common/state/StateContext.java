package cn.servicex.common.state;

import cn.servicex.common.state.param.BaseParam;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class StateContext <P extends BaseParam> {
    private StateEvent event;
    private P param;
}
