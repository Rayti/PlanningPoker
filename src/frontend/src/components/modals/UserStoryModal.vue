<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Manage User Stories</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">

          <div class="accordion accordion-flush" id="accordionFlushExample">
            <div class="accordion-item" v-for="(story, index) in userStories" :key="story.id">
              <h2 class="accordion-header" id="flush-headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="'#flush-collapseOne'+index" aria-expanded="false" aria-controls="flush-collapseOne">
                  #{{index + 1}} {{story.title}}
                </button>
              </h2>
              <div :id="'flush-collapseOne'+index" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body">
                  <h5 align="left">Tasks:</h5>
                  <ul v-for="(task, index) in story.tasks" :key="task.id" class="justify-content-start">
                    <li class = "taskList">#{{index+1}} task</li>
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
                        <button type="button" class="btn btn-outline-secondary btn-sm col-4 " @click="addNewTaskToStoryInsideClick">Add</button>
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
                <li class = "taskList">#{{index+1}} task</li>
                </ul>
              <div class="form-floating mb-3">
                <input class="form-control" id="floatingInputTask" v-model="newTaskInput">
                <label for="floatingInputTask">Add task to the story</label>
                <div class="row justify-content-end border-bottom pb-2 border-info">
                  <div class=" newStoryAddBtn">
                    <button type="button" class="btn btn-outline-secondary btn-sm col-4 " @click="addNewTaskToStoryClick">Add</button>
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
</template>

<script>
export default {
  name: "UserStoryModal",
  data () {
    return {
      userStories :[
        {id: 1, title : "one" , tasks  :["login","logout"]},
      ],
      newStoryInput:"",
      newTaskInput:"",
      newTaskInsideInput:"",
      tasks:[],
    }
  },
  methods:{
    closeModal(){
      this.$emit("closeUserStoryModal");
    },
    addNewStoryClick(){
      this.userStories.push({id:3,title: this.newStoryInput,tasks:this.tasks,body:"5"});
      this.newStoryInput = "";
      this.tasks=[];
    },
    addNewTaskToStoryClick(){
      this.tasks.push({id:0, taskTitle: this.newTaskInput});
      this.newTaskInput="";

    },
    addNewTaskToStoryInsideClick(){
      this.newTaskInsideInput="";
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
</style>