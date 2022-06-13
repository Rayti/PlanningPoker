<template>
  <div class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <h5>Please confirm that you know what you are doing </h5>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="closeModal" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-danger" @click="confirmDelete" data-bs-dismiss="modal">Confirm delete</button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ConfirmDeleteModal",
  methods: {
    closeModal() {
      this.$emit('confirm', {
        result: false
      })
    },
    confirmDelete() {
      this.$emit('confirm', {
        result: true
      })
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