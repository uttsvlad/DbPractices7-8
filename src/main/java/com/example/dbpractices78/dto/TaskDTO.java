package com.example.dbpractices78.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Vlad Utts
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @NotEmpty(message = "Описание задачи не может быть пустым!")
    @Size(min = 10, max = 300, message = "Размер описания должен быть между 10 и 300 символами!")
    private String description;
    @NotEmpty(message = "Дата дедлайна не может быть пустой!")
    private String deadlineDate;
    private Long priorityId;
    private Long contactPersonId;
    private Long executorId;
    private Long contractId;
}
