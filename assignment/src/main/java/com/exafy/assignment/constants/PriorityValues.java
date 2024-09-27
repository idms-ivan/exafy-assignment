package com.exafy.assignment.constants;

import com.exafy.assignment.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriorityValues {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    final String priorityValue;

    public static PriorityValues checkIsValid(String priority){
        for(PriorityValues priorityValues : PriorityValues.values()){
            if(priorityValues.getPriorityValue().equalsIgnoreCase(priority)){
                return priorityValues;
            }
        }
        throw new BadRequestException("Invalid priority value: " + priority);
    }
}