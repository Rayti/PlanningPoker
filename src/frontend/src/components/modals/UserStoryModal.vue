<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog  modal-dialog-centered modal-dialog-scrollable modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Manage User Stories  </h5>
          <div class="dropdown dd">
            <button class="btn btn-outline-success dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
              Actions
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
              <li><a class="dropdown-item" href="#">Import Stories</a></li>
              <li><a class="dropdown-item" href="#">Export Stories</a></li>
            </ul>
          </div>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">

          <div class="accordion accordion-flush" id="accordionFlushExample">
            <div class="accordion-item" v-for="(story, index) in userStories" :key="story.id">
              <h2 class="accordion-header" :id="'flush-headingOne'+index">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="'#flush-collapseOne'+index" aria-expanded="false" :aria-controls="'flush-collapseOne'+index">
                  #{{index + 1}} {{story.description}}
                  <button type="button" :id="story.id" class="btn btn-sm btn-outline-success btnModal" @click="onChooseStory">Choose</button>
                  <button type="button"  class="btn btn-sm btn-outline-danger btnModal" @click="onDeleteStoryClick(story.id)">Delete</button>
                  <button type="button"  class="btn btn-sm btn-outline-info btnModal" @click="onEditStoryClick(story)">Edit</button>
                </button>
              </h2>
              <div :id="'flush-collapseOne'+index" class="accordion-collapse collapse" :aria-labelledby="'flush-headingOne'+index" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body">
                  <h5 align="left">Tasks:</h5>
                  <ul v-for="(task, index) in story.tasks" :key="task.id" class="justify-content-start">
                    <li class = "taskList">#{{index+1}} {{task.description}}
                    <button type="button" :id="story.id" class="btn btn-sm btn-outline-danger btnModal" @click="onDeleteTaskClick(story.id, task)">Delete</button>
                    </li>
                  </ul>
                  <div class="row justify-content-end">
                    <div class=" newStoryBtn">
                      <button type="button" class="btn btn-light btn-sm col-4 " data-bs-toggle="collapse" data-bs-target="#collapseAddTaskToStoryInside" aria-controls="collapseAddTaskToStoryInside">+</button>
                    </div></div>
                  <div class="collapse" id="collapseAddTaskToStoryInside">
                  <div class="form-floating mb-3">
                    <input class="form-control" id="floatingInputTaskInside" v-model="newTaskInsideInput">
                    <label for="floatingInputTask">Add task to the story</label>
                    <div class="row justify-content-end ">
                      <div class=" newStoryAddBtn">
                        <button type="button" class="btn btn-outline-secondary btn-sm col-4 " @click="addNewTaskToStoryInsideClick(story.id)">Add</button>
                      </div></div>
                  </div>
                  </div>

                </div>
              </div>
            </div>
            <div class="row justify-content-end">
              <div class=" newStoryBtn">
                <button type="button" class="btn btn-light btn-sm col-4 " data-bs-toggle="collapse" data-bs-target="#collapseAddUSerStory" aria-controls="collapseAddUSerStory"> +New Story</button>
              </div></div>
          </div>
          <div class="collapse" id="collapseAddUSerStory">
            <div class="card card-body">
              <h5 align="left">Enter new User Story </h5>
              <div class="form-floating mb-3">
                <input  class="form-control" id="floatingInput" v-model="newStoryInput">
                <label for="floatingInput">Create new story</label>
              </div>
              <div class="row justify-content-start">
                <div class=" newTaskBtn">
                  <button type="button" class="btn btn-light btn-sm col-4 " data-bs-toggle="collapse" data-bs-target="#collapseAddTaskToStory" aria-controls="collapseAddTaskToStory">Tasks</button>
                </div></div>
              <div class="collapse" id="collapseAddTaskToStory">

                <h5 align="left" class="border-top pt-2 mt-2 border-info">Tasks to the story</h5>

              <ul v-for="(task, index) in tasks" :key="task.id" class="justify-content-start">
                <li class = "taskList">#{{index+1}} {{task.description}}</li>
                </ul>
              <div class="form-floating mb-3">
                <input class="form-control" id="floatingInputTask" v-model="newTaskInput">
                <label for="floatingInputTask">Add task to the story</label>
                <div class="row justify-content-end border-bottom pb-2 border-info">
                  <div class=" newStoryAddBtn">
                    <button type="button" class="btn btn-outline-secondary btn-sm col-4 " @click="addNewTaskToNewStoryClick">Add</button>
                  </div></div>
              </div>

              </div>

              <div class="row justify-content-end">
                <div class=" newStoryAddBtn">
              <button type="button" class="btn btn-outline-primary btn-sm col-4 " @click="addNewStoryClick">Create Story</button>
                </div></div>
            </div>
          </div>
        </div>
        <div class="modal-footer">

          <button type="button" class="btn btn-primary" @click = "closeModal" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <EditStoryModal v-if="displayEditStoryModal" :story="this.editedStory" @close-modal-event="hideModal" @edit = "editStoryHandler"></EditStoryModal>
</template>

<script>
import { v4 as uuid4 } from 'uuid'
import EditStoryModal from "@/components/modals/EditStoryModal";

export default {
  name: "UserStoryModal",
  components: {
    EditStoryModal,

  },
  props: {
    storyID: String
  },
  inject: ['webHttpService','webSocketService'],
  data () {
    return {
      userStories : this.$store.getters.getStoriesAll,
      newStoryInput:"",
      newTaskInput:"",
      newTaskInsideInput:"",
      tasks:[],
      newStoryId:'',
      displayEditStoryModal:false,
      editedStory:{},
    }
  },
  methods:{
    closeModal(){
      this.$emit("closeUserStoryModal");
    },
    addNewStoryClick(){
      if(!this.newStoryId){
        this.newStoryId=Date.now();
      }
      if(this.newStoryInput !== '') {
        this.webSocketService.addNewUserStory(this.$store.state.roomName, this.$store.state.userName, this.newStoryId, this.newStoryInput, this.tasks)

      }
      this.newStoryInput = "";
      this.tasks=[];
      this.newStoryId='';
    },
    addNewTaskToNewStoryClick(){
      let taskId=Date.now();
      this.tasks.push({id:taskId, description: this.newTaskInput});

      this.newTaskInput="";

    },
    addNewTaskToStoryInsideClick(storyId){
      let taskId=Date.now();
      if(this.newTaskInsideInput !== '') {
        this.webSocketService.addNewTask(this.$store.state.roomName, this.$store.state.userName,
            storyId, taskId, this.newTaskInsideInput)

      }
      this.newTaskInsideInput="";
    },
    onChooseStory(e){
      this.$emit('chooseStory',{id: e.target.id});
    },
    onDeleteStoryClick(storyid){
      this.webSocketService.deleteUserStory(this.$store.state.roomName, this.$store.state.userName, storyid)


    },
    onDeleteTaskClick(storyid, task){
      let arg = { storyID : storyid, task: task}
      this.$store.commit('deleteTaskStory', arg);
    },
    onEditStoryClick(story){
      this.displayEditStoryModal=true;
      this.editedStory = story;
    },
    hideModal() {
      this.displayEditStoryModal = false;
    },
    editStoryHandler($event){
      this.webSocketService.updateUserStory(this.$store.state.roomName, this.$store.state.userName,
          $event.arg.storyId,$event.arg.newName)

      this.displayEditStoryModal = false;
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
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
.newTaskBtn{
  width: 25.0rem;
  justify-content: flex-start;
  display: flex;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
}
.taskList{
  justify-content: flex-start;
  alignment: left;
  display: flex;
}
.btnModal{
  margin-left: 2px;
}
.dd{
  /*z-index: 1100;*/
  margin-left: 10px;
}
</style>