package com.xuhe.platform.common.helper;

import com.google.common.collect.Lists;
import com.xuhe.platform.common.model.bo.ParameterInvalidItem;



import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/12
 * @description:
 */
public class ParameterInvalidItemHelper {

    public static List<ParameterInvalidItem> convertBindingResultToMapParameterInvalidItemList(BindingResult bindingResult){
        if(null == bindingResult){
            return null;
        }
        List<ParameterInvalidItem> parameterInvalidItems = Lists.newArrayList();

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        for(FieldError fieldError : fieldErrorList){
            ParameterInvalidItem parameterInvalidItem = new ParameterInvalidItem();
            parameterInvalidItem.setFieldName(fieldError.getField());
            parameterInvalidItem.setMessage(fieldError.getDefaultMessage());
            parameterInvalidItems.add(parameterInvalidItem);
        }
        return parameterInvalidItems;
    }

}
