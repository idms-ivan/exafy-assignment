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


    public static CategoryValues checkIsValid(String category){
        for(CategoryValues categoryValues : CategoryValues.values()){
            if(categoryValues.getCategoryValues().equalsIgnoreCase(category)){
                return categoryValues;
            }
        }
        throw new BadRequestException("Invalid category value: " + category);
    }
}
