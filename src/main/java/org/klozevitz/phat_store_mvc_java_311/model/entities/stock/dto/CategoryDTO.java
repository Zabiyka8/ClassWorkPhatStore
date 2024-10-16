package org.klozevitz.phat_store_mvc_java_311.model.entities.stock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private int id;
    private String name;
}
