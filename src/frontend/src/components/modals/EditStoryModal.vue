<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Update story</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="form mb-3">
            <label class="align-content-start" for="nameStoryInput">Story Name</label>
            <input  class="form-control" id="nameStoryInput" v-model="nameStoryInput" :placeholder="this.story.description">
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeModal" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-success" @click="update" data-bs-dismiss="modal">Update</button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>

import {v4 as uuid4} from "uuid";

export default {
  name: "EditStoryModal",
  props: {
    story: Object,
  },
data () {
  return {
    nameStoryInput:"",
  }
},
  methods:{
    closeModal() {
      this.$emit('close-modal-event');
    },
    update(){
      if(this.newTaskInsideInput !== '') {
        let arg = { storyId :  this.story.id ,newName: this.nameStoryInput};
      this.$emit('edit',{
        arg: arg
      })





      }
        this.nameStoryInput="";


    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
  z-index: 2000;
}
</style>