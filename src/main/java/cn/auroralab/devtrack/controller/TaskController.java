package cn.auroralab.devtrack.controller;

import cn.auroralab.devtrack.annotation.SkipTokenVerification;
import cn.auroralab.devtrack.dto.HeatMapData;
import cn.auroralab.devtrack.dto.TaskDTO;
import cn.auroralab.devtrack.enumeration.StatusCode;
import cn.auroralab.devtrack.exception.ResponseException;
import cn.auroralab.devtrack.form.NewTaskForm;
import cn.auroralab.devtrack.service.TaskService;
import cn.auroralab.devtrack.util.JwtUtils;
import cn.auroralab.devtrack.util.PageInformation;
import cn.auroralab.devtrack.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) { this.taskService = taskService; }

    @PostMapping("/new")
    public StatusCode create(@RequestHeader(value = "Authorization") String authorization, @RequestBody NewTaskForm form) {
        String creatorUUID = JwtUtils.getUserUUID(authorization);

        StatusCode statusCode = StatusCode.SUCCESS;

        try {
            taskService.newTask(creatorUUID, form);
        } catch (ResponseException e) {
            statusCode = e.statusCode;
        }

        return statusCode;
    }

    @GetMapping("/getOnePageFromProject")
    @SkipTokenVerification
    public ResponseVO<PageInformation<TaskDTO>> getTaskList(String projectUUID, int pageNum, int pageSize) {
        StatusCode statusCode = StatusCode.SUCCESS;
        PageInformation<TaskDTO> pageInformation = null;

        try {
            pageInformation = taskService.getTaskList(projectUUID, pageNum, pageSize);
        } catch (ResponseException e) {
            statusCode = e.statusCode;
        }

        return new ResponseVO<>(statusCode, pageInformation);
    }

    @GetMapping("/getHeatMap")
    @SkipTokenVerification
    public ResponseVO<List<HeatMapData>> getHeatMap(String userUUID) {
        StatusCode statusCode = StatusCode.SUCCESS;
        List<HeatMapData> list = null;

        try {
            list = taskService.getTaskCountFinishedInThePastYear(userUUID);
        } catch (ResponseException e) {
            statusCode = e.statusCode;
        }

        return new ResponseVO<>(statusCode, list);
    }
}