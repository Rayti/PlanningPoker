<template>
  <!-- <div class="card" @click="click"> -->
    <div class="planning-poker-card" @click="onClick" :class="{ selected: isSelected }">
      <h1 class="estimation ">{{ estimation }}</h1>
    </div>
  <!-- </div> -->
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

.planning-poker-card {
  display: flex;
  overflow: hidden;
  backface-visibility: hidden;
  align-items: center;
  justify-content: center;
  border: 2px solid black;
  border-radius: .6rem;
  width: 4.8rem;
  height: 6rem;
  background-color: white;
  background-image: url("../assets/card-texture.png");
  font-size: 19px;
  font-weight: 700;
}

.planning-poker-card.selected {
  background-color: rgb(200, 200, 200);
  background-image: none;
}

.estimation {
  font-family: Helvetica;
  text-align: center;
  color: rgb(214, 10, 81);
}
</style>