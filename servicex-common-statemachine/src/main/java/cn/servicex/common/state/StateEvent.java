package cn.servicex.common.state;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateEvent {
    private String tempId;
    private String state;
    private String event;
}
