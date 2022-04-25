<template>
  <div class="card" @click="onClick">
    <div class="card-face" :class="{ selected: isSelected }">
      <h1 class="estimation">{{ estimation }}</h1>
    </div>
  </div>
</template>

<script>
export default {
    name: 'PlanningPokerCard',
    props: {
      estimation: String,
      isSelected: Boolean
    }, // ['estimation']
    methods: {
      onClick() {
          this.$emit('select')
      }
    }
}
</script>

<style scoped>
.card {
  position: relative;
}
.card-face {
  width: 100px;
  height: 100px;
  position: absolute;
  top: 0;
  border-radius: 10px;
  border: 2px solid rgb(214, 10, 81);
  background-color: rgb(255, 255, 255);
  /* color: white; */
}
.card-face.selected {
  background-color: rgb(200, 200, 200);
  /* transform:rotateY(180deg); */
}

.estimation {
  font-family: Helvetica;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: rgb(214, 10, 81);
}
</style>