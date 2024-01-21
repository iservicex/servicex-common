package cn.servicex.common.state.processor;

import cn.servicex.common.state.StateActionStep;
import cn.servicex.common.state.StateContext;
import cn.servicex.common.state.param.BaseParam;
import cn.servicex.common.state.result.ServiceResult;

public abstract class AbstractStateProcessor<T, P extends BaseParam> implements StateProcessor<T, P>, StateActionStep<T, P> {
    public abstract boolean filter(StateContext<P> context);

    @Override
    public ServiceResult<T> action(StateContext<P> context) throws Exception {
        ServiceResult<T> result = null;
        // 数据准备
        this.prepare(context);
        // 串行校验器
        result = this.check(context);
        if (!result.isSuccess()) {
            return result;
        }
        // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
        String nextState = this.getNextState(context);
        // 业务逻辑
        result = this.action(nextState, context);
        if (!result.isSuccess()) {
            return result;
        }
        // 持久化
        result = this.save(nextState, context);
        if (!result.isSuccess()) {
            return result;
        }
        // after
        this.after(context);
        return result;
    }
}
