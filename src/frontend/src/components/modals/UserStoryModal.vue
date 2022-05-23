<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Manage User Stories</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="accordion accordion-flush" id="accordionFlushExample">
            <div class="accordion-item" v-for="(faq, index) in filteredfaqs" :key="faq.id">
              <h2 class="accordion-header" id="flush-headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="'#flush-collapseOne'+index" aria-expanded="false" aria-controls="flush-collapseOne">
                  {{index}} {{faq.title}}
                </button>
              </h2>
              <div :id="'flush-collapseOne'+index" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                <div class="accordion-body">
                  {{faq.body}}
                </div>
              </div>
            </div>

          </div>
          <button type="button" class="btn btn-outline-info" data-bs-toggle="collapse" data-bs-target="#collapseAddUSerStory" aria-expanded="false" aria-controls="collapseExample">New User Story</button>
          <div class="collapse" id="collapseAddUSerStory">
            <div class="card card-body">
              Some placeholder content for the collapse component. This panel is hidden by default but revealed when the user activates the relevant trigger.
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click = "closeModal" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" @click="createNewRoom" data-bs-dismiss="modal">Go</button>
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
      filteredfaqs :[
        {id: 1, title : "one" , body  :"1"},
        {id: 2, title : "two" , body  :"2"},
        {id: 3, title : "tree" , body  :"3"}
      ]
    }
  },
  methods:{
    closeModal(){
      this.$emit("closeUserStoryModal");
    }
  }
}
</script>

<style scoped>
.modal {
  display: block;
}
</style>