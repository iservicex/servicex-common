package cn.servicex.common.state.processor;

import cn.servicex.common.state.StateContext;
import cn.servicex.common.state.param.CreateOrderParam;
import cn.servicex.common.state.result.ServiceResult;

public class OrderCreatedProcessor extends AbstractStateProcessor<String, CreateOrderParam>{

    @Override
    public ServiceResult<String> check(StateContext<CreateOrderParam> context) {
        return null;
    }


    @Override
    public String getNextState(StateContext<CreateOrderParam> context) {
        return null;
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderParam> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult<String> save(String nextState, StateContext<CreateOrderParam> context) throws Exception {
        return null;
    }

    @Override
    public void after(StateContext<CreateOrderParam> context) {

    }

    @Override
    public boolean filter(StateContext<CreateOrderParam> context) {
        return false;
    }
}
