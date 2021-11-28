package com.brichev.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private Integer id;
    private String description;
    private Boolean isCompleted;
    private Integer listId;
}
