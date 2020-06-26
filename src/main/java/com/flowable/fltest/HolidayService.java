package com.flowable.fltest;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.engine.TaskService;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class HolidayService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public ProcessInstance startProcess(BigDecimal orderAmount, Integer nrOfHolidays, String description) {
        log.info("Received order for " + orderAmount);
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderAmount", orderAmount);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        return runtimeService.startProcessInstanceByKey("orderReview", variables);
    }

    public void completeTask(String taskId, boolean approved, String comment) {
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        taskService.addComment(taskId, processInstanceId, comment);
        HashMap<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("reviewStatus", approved ? "approved" : "rejected");
        taskVariables.put("state", "VA");
        log.info("Will be approved? -> " + approved);
        taskService.complete(taskId, taskVariables);
    }

    public List<Task> getTasksToAssign(String userName, List<String> userGroups, ShowTasksMode showTasksMode) {
        TaskQuery taskQuery = taskService.createTaskQuery().or();
        if (showTasksMode == ShowTasksMode.ALL || showTasksMode == ShowTasksMode.MY) {
            taskQuery.taskAssignee(userName);
        }
        if (showTasksMode == ShowTasksMode.ALL || showTasksMode == ShowTasksMode.UNCLAIMED) {
            taskQuery.taskCandidateGroupIn(userGroups)
                     .taskCandidateUser(userName);
        }
        return taskQuery.list();
    }

    public void claimTask(String taskId, String userName) {
        taskService.claim(taskId, userName);
    }

    public void unClaimTask(String taskId) {
        taskService.unclaim(taskId);
    }

}
