package com.brichev.mvc.model.enitity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@EqualsAndHashCode(exclude = {"taskList"})
@ToString(exclude = {"taskList"})
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskListEntity taskList;
}
