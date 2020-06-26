package com.flowable.fltest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class MyTask {
    private String id;
    private String name;
    private String owner;
    private int priority;
    private String taskDefinitionId;
    private String taskDefinitionKey;
    private Date gueDate;
    private String assignee;
}
