package com.example.planningpoker.controller.task;

import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.controller.task.message.TaskDescriptionMessage;
import com.example.planningpoker.controller.task.message.TaskMessage;
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
public class TaskController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    public static final String TASK_MESSAGE = "TaskMessage";
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TaskController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/task-create")
    public void createTask(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String storyId, TaskDescriptionMessage taskDescriptionMessage){
        //TODO MIKA
        TaskMessage msg = new TaskMessage("CreateTaskMessage", "1", "testTaskDescription");
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/{taskId}/task-update/description")
    public void updateTask(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String storyId, @DestinationVariable String taskId, TaskDescriptionMessage taskDescriptionMessage){
        //TODO MIKA
        TaskMessage msg = new TaskMessage("UpdateTaskMessage", taskId, taskDescriptionMessage.getDescription());
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
    }

    @GetMapping("/api/poker/{roomName}/{userName}/{storyId}/{taskId}/retrieve-task")
    @ResponseBody
    public TaskMessage retrieveTask(@PathVariable String roomName, @PathVariable String userName,
                             @PathVariable String storyId, @PathVariable String taskId) {
        //TODO MIKA
        return  new TaskMessage(TASK_MESSAGE, taskId, "retrievedTaskTestDescription");
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/{taskId}/task-delete")
    public void deleteTask(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String storyId, @DestinationVariable String taskId){
        //TODO MIKA
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), new SuccessMessage(true));
    }
}
