package com.exafy.assignment.constants;

import com.exafy.assignment.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusValues {
    PENDING("pending"),
    IN_PROGRESS("in progress"),
    COMPLETED("Completed"),
    OVERDUE("overdue");

    final String statusValues;

    public static StatusValues checkIsValid(String status){
        for(StatusValues statusValues : StatusValues.values()){
            if(statusValues.getStatusValues().equalsIgnoreCase(status)){
                return statusValues;
            }
        }
        throw new BadRequestException("Invalid status value: " + status);
    }
}
