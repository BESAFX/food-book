package com.besafx.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonOptions {

    private String lang;

    private String dateType;

    private String style;

    private String iconSet;

    private String iconSetType;
}
