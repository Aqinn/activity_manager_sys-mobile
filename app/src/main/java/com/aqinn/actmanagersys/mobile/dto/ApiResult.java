package com.aqinn.actmanagersys.mobile.dto;

/**
 * API返回数据格式
 * @param <T>
 */
public class ApiResult<T> {

   public boolean success;
   public String errMsg;
   public T data;

   @Override
   public String toString() {
      return "ApiResult{" +
              "success=" + success +
              ", errMsg='" + errMsg + '\'' +
              ", data=" + data +
              '}';
   }

}
