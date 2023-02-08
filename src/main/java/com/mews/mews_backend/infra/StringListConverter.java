package com.mews.mews_backend.infra;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return String.join(",", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        // null인 경우 빈 리스트 리턴
        if(joined == null){
            return Collections.emptyList();
        }
        return new ArrayList<>(Arrays.asList(joined.split(",")));
    }

}
