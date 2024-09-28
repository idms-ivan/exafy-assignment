package com.exafy.assignment.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class BeanCopyUtils {


    public static void copyNonNullOrZeroProperties(final Object source, final Object target) {
        BeanUtils.copyProperties(source, target, getNullOrZeroPropertyNames(source));
    }

    private static String[] getNullOrZeroPropertyNames(final Object source) {
        final BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        final Set<String> emptyNames = new HashSet<>();

        for (final PropertyDescriptor descriptor : sourceWrapper.getPropertyDescriptors()) {
            final Object value = sourceWrapper.getPropertyValue(descriptor.getName());

            if (value == null ||
                    (value instanceof Double && (Double) value == 0.0) ||
                    (value instanceof Integer && (Integer) value == 0)) {
                emptyNames.add(descriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}
