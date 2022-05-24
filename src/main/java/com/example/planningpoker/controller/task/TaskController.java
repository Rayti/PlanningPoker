package com.example.planningpoker.controller.task;

import com.example.planningpoker.controller.MessageMapper;
import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.controller.task.message.*;
import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.Task;
import com.example.planningpoker.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@Slf4j
public class TaskController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    public static final String TASK_MESSAGE = "TaskMessage";
    private SimpMessagingTemplate messagingTemplate;
    private RoomService roomService;

    @Autowired
    public TaskController(RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/task-create")
    public void createTask(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String storyId, TaskDescriptionMessage taskDescriptionMessage){
        //DONE
        log.info("/api/poker/{}/{}/{}/task-create", roomName, userName, storyId);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName) && room.getStory(Integer.parseInt(storyId)) != null){
            Task task = new Task(Integer.parseInt(taskDescriptionMessage.getTaskId()), taskDescriptionMessage.getDescription());
            room.getStory(Integer.parseInt(storyId)).getTasks().add(task);
            CreateTaskMessage msg = new CreateTaskMessage("CreateTaskMessage", taskDescriptionMessage.getTaskId(), taskDescriptionMessage.getDescription());
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/{}/task-create SENT CreateTaskMessage to subscribers", roomName, userName, storyId);
        }

    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/{taskId}/task-update/description")
    public void updateTask(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String storyId, @DestinationVariable String taskId, TaskDescriptionMessage taskDescriptionMessage){
        //DONE
        log.info("/api/poker/{}/{}/{}/{}/task-update/description", roomName, userName, storyId, taskId);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)
                && room.getStory(Integer.parseInt(storyId)) != null
                && room.getStory(Integer.parseInt(storyId)).getTask(Integer.parseInt(taskId)) != null) {

            Task task = room.getStory(Integer.parseInt(storyId)).getTask(Integer.parseInt(taskId));
            task.setDescription(taskDescriptionMessage.getDescription());

            UpdateTaskDescriptionMessage msg = new UpdateTaskDescriptionMessage("UpdateTaskDescriptionMessage", taskId, task.getDescription());
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/{}/{}/task-update/description SENT UpdateTaskDescriptionMessage to subscribers", roomName, userName, storyId, taskId);
        }

    }

    @GetMapping("/api/poker/{roomName}/{userName}/{storyId}/{taskId}/retrieve-task")
    @ResponseBody
    public TaskMessage retrieveTask(@PathVariable String roomName, @PathVariable String userName,
                             @PathVariable String storyId, @PathVariable String taskId) {
        //DONE
        log.info("/api/poker/{}/{}/{}/{}/retrieve-task", roomName, userName, storyId, taskId);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)
                && room.getStory(Integer.parseInt(storyId)) != null
                && room.getStory(Integer.parseInt(storyId)).getTask(Integer.parseInt(taskId)) != null) {
            Task task = room.getStory(Integer.parseInt(storyId)).getTask(Integer.parseInt(taskId));

            return MessageMapper.toTaskMessage(task);
        }
        log.error("/api/poker/{}/{}/{}/{}/retrieve-task  - no room or user", roomName, userName, storyId, taskId);
        return null;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/{taskId}/task-delete")
    public void deleteTask(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String storyId, @DestinationVariable String taskId){
        log.info("/api/poker/{}/{}/{}/{}/task-delete", roomName, userName, storyId, taskId);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)
                && room.getStory(Integer.parseInt(storyId)) != null
                && room.getStory(Integer.parseInt(storyId)).getTask(Integer.parseInt(taskId)) != null) {

            room.getStory(Integer.parseInt(storyId)).deleteTask(Integer.parseInt(taskId));
            DeleteTaskMessage msg = new DeleteTaskMessage("DeleteTaskMessage", taskId);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/{}/{}/task-delete SENT DeleteTaskMessage to subscribers", roomName, userName, storyId, taskId);
        }
    }
}
