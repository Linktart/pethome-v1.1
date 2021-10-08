package cn.chengli.util;

import lombok.Data;

/**
 * @chengli
 */
@Data
public class AjaxResult {
    private Boolean success = true;
    private String msg;
    private Object data;

    public static AjaxResult createSuccess(String msg,Object data){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setData(data);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }
    public static AjaxResult createError(String msg){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(false);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }
}
