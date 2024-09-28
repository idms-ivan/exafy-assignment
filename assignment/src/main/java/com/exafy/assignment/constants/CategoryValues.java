package com.exafy.assignment.constants;

import com.exafy.assignment.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryValues {
    WORK("work"),
    PERSONAL("personal"),
    OTHERS("others");

    final String categoryValues;


    public static void checkIsValid(String category){
        for(CategoryValues categoryValues : CategoryValues.values()){
            if(categoryValues.getCategoryValues().equalsIgnoreCase(category)){
                return;
            }
        }
        throw new BadRequestException("Invalid category value: " + category + " Allowed categories are: work, personal, others");
    }
}
