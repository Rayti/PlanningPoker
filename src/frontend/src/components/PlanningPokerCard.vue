<template>
  <!-- <div class="card" @click="click"> -->
    <div class="planning-poker-card" @click="click" :class="{ selected: isSelected }">
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
      click() {
          this.$emit('select')
      }
    }
}
</script>

<style scoped>

.planning-poker-card {
  border-radius: .6rem;
  border: 2px solid black;
  font-size: 19px;
  height: 8rem;
  width: 4.8rem;
  align-items: center;
  backface-visibility: hidden;
  background: #fff;
  /* border-radius: .6rem; */
  display: flex;
  font-weight: 700;
  height: 6rem;
  justify-content: center;
  overflow: hidden;
}

.planning-poker-card.selected {
  background-color: rgb(200, 200, 200);
}

.estimation {
  font-family: Helvetica;
  text-align: center;
  color: rgb(214, 10, 81);
}
</style>