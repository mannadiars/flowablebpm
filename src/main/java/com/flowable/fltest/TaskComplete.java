package com.flowable.fltest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskComplete {

    private String taskId;
    private boolean isApproved;
    private String comment;

}
