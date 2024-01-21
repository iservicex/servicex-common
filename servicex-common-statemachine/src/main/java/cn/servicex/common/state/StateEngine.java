package cn.servicex.common.state;

import cn.servicex.common.state.param.BaseParam;
import cn.servicex.common.state.processor.AbstractStateProcessor;
import cn.servicex.common.state.result.ServiceResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

public class StateEngine<P extends BaseParam, T> {
    private final Map<String, Map<String, Map<String, List<AbstractStateProcessor<T, P>>>>> holder = new HashMap<>();

    /**
     * 注册
     *
     * @param stateEvent 状态事件
     * @param processor  事件处理器
     */
    public void register(StateEvent stateEvent, AbstractStateProcessor<T, P> processor) {
        Map<String, Map<String, List<AbstractStateProcessor<T, P>>>> stateMap = holder.get(stateEvent.getTempId());
        if (Objects.nonNull(stateMap)) {
            Map<String, List<AbstractStateProcessor<T, P>>> eventMap = stateMap.get(stateEvent.getState());
            if (Objects.isNull(eventMap)) {
                eventMap = new HashMap<>();
                List<AbstractStateProcessor<T, P>> list = new ArrayList<>();
                list.add(processor);
                eventMap.put(stateEvent.getState(), list);
            } else {
                List<AbstractStateProcessor<T, P>> list = eventMap.get(stateEvent.getEvent());
                if (CollectionUtils.isEmpty(list)) {
                    list = new ArrayList<>();
                    list.add(processor);
                } else {
                    list.add(processor);
                }
                eventMap.put(stateEvent.getEvent(), list);
            }
        } else {
            stateMap = new HashMap<>();
            Map<String, List<AbstractStateProcessor<T, P>>> eventMap = new HashMap<>();
            List<AbstractStateProcessor<T, P>> list = new ArrayList<>();
            list.add(processor);
            eventMap.put(stateEvent.getEvent(), list);
            stateMap.put(stateEvent.getState(), eventMap);
            holder.put(stateEvent.getTempId(), stateMap);
        }

    }


    public ServiceResult<T> transfer(StateEvent event, P param) throws Exception {
        StateContext<P> context = new StateContext<>(event, param);
        AbstractStateProcessor<T, P> processor = getStateProcessor(context);
        if (Objects.isNull(processor)) {
            throw new RuntimeException("can't find event processor!");
        }
        return processor.action(context);
    }

    private AbstractStateProcessor<T, P> getStateProcessor(StateContext<P> context) {
        Map<String, Map<String, List<AbstractStateProcessor<T, P>>>> nodeMap = holder.get(context.getEvent().getTempId());
        if (MapUtils.isEmpty(nodeMap)) {
            return null;
        }
        Map<String, List<AbstractStateProcessor<T, P>>> eventMap = nodeMap.get(context.getEvent().getState());
        if (MapUtils.isEmpty(eventMap)) {
            return null;
        }
        List<AbstractStateProcessor<T, P>> processorList = eventMap.get(context.getEvent().getEvent());
        if (CollectionUtils.isEmpty(processorList)) {
            return null;
        }

        List<AbstractStateProcessor<T, P>> list = new ArrayList<>();

        for (AbstractStateProcessor<T, P> processor : processorList) {
            if (processor.filter(context)) {
                list.add(processor);
            }
        }

        if (list.size() > 1) {
            throw new RuntimeException("event processor is more than one!");
        }
        return null;
    }
}
