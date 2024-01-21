package cn.servicex.common.state;

import cn.servicex.common.state.param.BaseParam;
import cn.servicex.common.state.result.ServiceResult;

public interface StateActionStep<T,P extends BaseParam> {
    /**
     * 准备数据
     */
    default void prepare(StateContext<P> context) {
    }
    /**
     * 校验
     */
    ServiceResult<T> check(StateContext<P> context);
    /**
     * 获取当前状态处理器处理完毕后，所处于的下一个状态
     */
    String getNextState(StateContext<P> context);
    /**
     * 状态动作方法，主要状态迁移逻辑
     */
    ServiceResult<T> action(String nextState, StateContext<P> context) throws Exception;
    /**
     * 状态数据持久化
     */
    ServiceResult<T> save(String nextState, StateContext<P> context) throws Exception;
    /**
     * 状态迁移成功，持久化后执行的后续处理
     */
    void after(StateContext<P> context);
}
