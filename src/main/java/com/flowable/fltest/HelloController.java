package com.flowable.fltest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Log4j2
public class HelloController {

    private final HolidayService holidayService;

    @GetMapping("/hello")
        public List<MyTask> hello(@RequestParam(name="showTasksMode", defaultValue = "ALL", required = false) ShowTasksMode showTasksMode) {
        log.info("get tasks");
        return holidayService.getTasksToAssign("alextrs", List.of("managers","users"), showTasksMode).stream().map(task ->
            new MyTask(task.getId(), task.getName(), task.getOwner(), task.getPriority(), task.getTaskDefinitionId(), task.getTaskDefinitionKey(), task.getDueDate(), task.getAssignee())
        ).collect(Collectors.toList());
    }

    @PostMapping("/hello")
    public String createTask(@RequestBody TaskCreate createTask) {
        log.info("create task");
        return holidayService.startProcess(createTask.getOrderAmount(), 1, "Some description").getId();
    }

    @PostMapping("/hello/complete")
    public Boolean completeTask(@RequestBody TaskComplete taskComplete) {
        log.info("complete task");
        holidayService.completeTask(taskComplete.getTaskId(), taskComplete.isApproved(), taskComplete.getComment());
        return true;
    }

    @PostMapping("/hello/claim")
    public Boolean claimTask(@RequestBody TaskClaim taskClaim) {
        log.info("claim task");
        holidayService.claimTask(taskClaim.getTaskId(), "alextrs");
        return true;
    }

    @PostMapping("/hello/unclaim")
    public Boolean unClaimTask(@RequestBody TaskClaim taskClaim) {
        log.info("un-claim task");
        holidayService.unClaimTask(taskClaim.getTaskId());
        return true;
    }

}