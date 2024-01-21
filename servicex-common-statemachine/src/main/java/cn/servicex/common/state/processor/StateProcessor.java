package cn.servicex.common.state.processor;

import cn.servicex.common.state.StateContext;
import cn.servicex.common.state.param.BaseParam;
import cn.servicex.common.state.result.ServiceResult;

public interface StateProcessor <T,C extends BaseParam >{
    ServiceResult<T> action (StateContext<C> context) throws Exception;
}
