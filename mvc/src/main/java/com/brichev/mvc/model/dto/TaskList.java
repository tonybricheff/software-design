package com.brichev.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskList {
    private Integer id;
    private String description;
    private Set<Task> tasks;
}
