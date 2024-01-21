package cn.servicex.common.state.result;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceResult <T>{
    private static final String SUCCESS = "200";
    private static final String FAIL = "100";

    private String code;
    private String msg;
    private T data;


    public Boolean isSuccess(){
        return SUCCESS.equals(code);
    }


}
