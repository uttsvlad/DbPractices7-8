package com.example.dbpractices78.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author Vlad Utts
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticFormDTO {
    private Long employeeId;
    @NotEmpty(message = "Дата не может быть пустой!")
    private String startDate;
    @NotEmpty(message = "Дата не может быть пустой!")
    private String endDate;
}
