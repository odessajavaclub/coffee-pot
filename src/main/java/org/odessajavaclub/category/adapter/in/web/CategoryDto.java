package org.odessajavaclub.category.adapter.in.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private long id;
    private String name;
}
