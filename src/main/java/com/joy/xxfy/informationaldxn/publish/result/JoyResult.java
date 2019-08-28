package com.joy.xxfy.informationaldxn.publish.result;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class JoyResult implements Serializable {
    private static final long serialVersionUID = -5607186023485351028L;

    private Boolean state;	//操作结果：bool
    private String message;	//返回信息：string
    private String detailMessage;	//返回信息：开发人员关注的信息
    private Object data = null;	//返回数据：object
    private int code;		//状态码：int

    private JoyResult(Notice notice, String detailMessage, Boolean state, Object data) {
        this.state = state;
        this.message = notice.getMessage();
        this.code = notice.getCode();
        this.data = data;
        this.detailMessage = detailMessage;
    }

    public  Map<String, Object> convertToMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("state", this.state);
        result.put("message", this.message);
        result.put("detailMessage", this.detailMessage);
        result.put("code", this.code);
        result.put("data", this.data);
        return result;
    }

    public static JoyResult buildSuccessResultWithData(Object data){
        return new JoyResult(Notice.EXECUTE_IS_SUCCESS,
                "",
                true,
                data
        );
    }

    public static JoyResult buildSuccessResultWithData(Object data, String detailMessage){
        return new JoyResult(Notice.EXECUTE_IS_SUCCESS,
                detailMessage,
                true,
                data
        );
    }


    public static JoyResult buildFailedResult(Notice notice, String detailMessage){
        return new JoyResult(notice,
                detailMessage,
                false,
                null
        );
    }

    public static JoyResult buildFailedResult(Notice notice, String detailMessage, Object data){
        return new JoyResult(notice,
                detailMessage,
                false,
                data
        );
    }

    public static JoyResult buildDefaultResult(){
        return new JoyResult(Notice.EXECUTE_IS_FAILED,
                "",
                false,
                null
                );
    }

    public static JoyResult buildSuccessResult(String detailMessage){
        return new JoyResult(Notice.EXECUTE_IS_SUCCESS,
                detailMessage,
                true,
                null
        );
    }

    public static JoyResult buildFailedResult(Notice notice){
        return new JoyResult(notice,
                notice.getMessage(),
                false,
                null
        );
    }

    public static JoyResult buildFailedResult(String detailMessage){
        return new JoyResult(Notice.EXECUTE_IS_FAILED,
                detailMessage,
                false,
                null
        );
    }





}
