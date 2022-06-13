<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Manage Tasks</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <h5 align="left">Tasks:</h5>
          <ul v-for="(task, index) in this.$store.state.currentStory.tasks" :key="task.id" class="justify-content-start">
            <li class = "taskList">#{{index+1}} {{task.description}}
            <button type="button" class="btn btn-sm btn-outline-danger btnModal btnModal" @click="deleteClick(task)">Delete</button>
            </li>
          </ul>
          <div class="row justify-content-end">
            <div class=" newStoryBtn">
              <button type="button" class="btn btn-outline-dark btn-sm col-4 " data-bs-toggle="collapse" data-bs-target="#collapseAddTaskToStoryInside" aria-controls="collapseAddTaskToStoryInside">+</button>
            </div></div>
          <div class="collapse" id="collapseAddTaskToStoryInside">
            <div class="form-floating mb-3">
              <input class="form-control" id="floatingInputTask" v-model="newTaskInput">
              <label for="floatingInputTask">Add task to the story</label>
              <div class="row justify-content-end ">
                <div class=" newStoryAddBtn">
                  <button type="button" class="btn btn-outline-secondary btn-sm col-4 " @click="addNewTaskClick">Add</button>
                </div></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { v4 as uuid4 } from 'uuid'
export default {
  name: "TasksModal",
  props: {
    storyID: String
  },
  inject: ['webHttpService','webSocketService'],
  data () {
    return {
      newTaskInput:"",
    //  tasks:(this.$store.state.currentStory.id)? this.$store.getters.getStoryTasks(this.$store.state.currentStory.id) : [],
    }
  },
  methods:{
    addNewTaskClick(){
      let taskId=Date.now();
      if(this.newTaskInput !== '') {
        this.webSocketService.addNewTask(this.$store.state.roomName, this.$store.state.userName,
            this.$store.state.currentStory.id, taskId, this.newTaskInput)
      }
      this.newTaskInput="";
    },
    deleteClick(task){
      let arg = { storyID : this.storyID, task: task}
      this.$store.commit('deleteTaskStory', arg)

    },
    closeModal(){
      this.$emit("closeTaskModal");
    },
  }
}

</script>

<style scoped>
.modal {
  display: block;
}
.taskList{
  justify-content: flex-start;
  alignment: left;
  display: flex;
}

.newStoryBtn {
  width: 25.0rem;
  justify-content: flex-end;
  display: flex;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
}
.newStoryAddBtn{
  width: 25.0rem;
  justify-content: flex-end;
  display: flex;
  margin-top: 0.5rem;
}
.btnModal{
  margin-left: 2px;
}

.modal-backdrop
{
  opacity:0.5 !important;
}
</style>