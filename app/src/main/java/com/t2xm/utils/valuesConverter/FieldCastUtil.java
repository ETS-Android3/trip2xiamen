package com.t2xm.utils.valuesConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FieldCastUtil {
    public static Object castStringToType(String value, Class<?> targetClass) throws IOException {
        if (targetClass == java.lang.Integer.class) {
            return Integer.parseInt(value);
        } else if (targetClass == java.lang.Double.class) {
            return Double.parseDouble(value);
        } else if (targetClass == java.lang.Float.class) {
            return Float.parseFloat(value);
        } else if (targetClass == java.lang.Long.class) {
            return Long.valueOf(value);
        } else if (targetClass == java.lang.String.class) {
            return String.valueOf(value);
        } else if (targetClass == java.lang.Boolean.class) {
            return Boolean.valueOf(value);
        } else if (targetClass == byte[].class) {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(value);
            objectOutput.flush();
            return byteOutput.toByteArray();
        } else {
            return targetClass.cast(value);
        }
    }
}
