package com.mink.projecttrrip.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String result;
    private String message;

    private T data;

    public static <T> ApiResponse<T> success(String message){
        return new ApiResponse<T>("success", message, null);
    }

    public static <T> ApiResponse<T> fail(String message){
        return new ApiResponse<T>("fail", message, null);
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<T>("success", message, data);
    }

}
