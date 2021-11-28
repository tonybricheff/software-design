package com.brichev.mvc.model.enitity;

import com.brichev.mvc.model.enitity.TaskEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "task_list")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"tasks"})
@ToString(exclude = {"tasks"})
public class TaskListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_list_id")
    private Integer taskListId;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasks;
}
