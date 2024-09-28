package com.exafy.assignment.constants;

import com.exafy.assignment.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusValues {
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    OVERDUE("overdue");

    final String statusValues;

    public static void checkIsValid(String status){
        for(StatusValues statusValues : StatusValues.values()){
            if(statusValues.getStatusValues().equalsIgnoreCase(status)){
                return;
            }
        }
        throw new BadRequestException("Invalid status value: " + status + " Allowed values are: pending, in_progress, completed, overdue");
    }
}
