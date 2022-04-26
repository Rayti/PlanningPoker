<template>
<div class="bottom-space">
    <div class="player-space">
        <div class="deck-label">
            <button class = "btn btn-outline-primary btn-lg" @click="confirmSelect">Select</button>
            <button class = "clear-bt btn btn-outline-secondary btn-lg" @click="clearSelections">Clear</button>
        </div>
        <div class="main-deck">
            <ul>
                <li v-for="fibb in fibonacci" :key="`card-${fibb}`">
                    <planning-poker-card :estimation="fibb" @select="selectCard(fibb)" 
                                            :isSelected="selectedCard === fibb"/>
                </li>
            </ul>
        </div>
        <div class="deck-label">
            <h2>Choose your card...</h2>
        </div>
    </div>
    </div>
</template>

<script>
import PlanningPokerCard from './PlanningPokerCard.vue';

export default {
    name: 'MainPlayerSpace',
    components: {
        PlanningPokerCard
    },
    data() {
        return {
            fibonacci: ['0', '1', '2', '3', '5', '8', '13', '21', '34', '55', '89', '?'],
            selectedCard: '',
            isSelectionConfirmed: false
        }
    },
    methods:{
        selectCard(fibb) {
            if (this.selectedCard === fibb) {
                this.selectedCard = ""
            } else {
                this.selectedCard = fibb
            }
        },
        confirmSelect() {
            this.isSelectionConfirmed = true
            this.emitValue();
        },
        emitValue(){
            this.$emit("selection", { selectedCard: this.selectedCard, isSelectionConfirmed: this.isSelectionConfirmed });
        },

      clearSelections(){
        this.selectedCard='';
        this.$emit("clear", {beCleared: true});
      }


    }
}
</script>

<style>
.bottom-space {
    flex-shrink: 0;
    height: 16rem;
    overflow: hidden;
    position: relative; 
    /* width: 100%; */
    /* bottom: 5px;
    display: grid;
    grid-template-rows: auto; */
    box-sizing: border-box;
    outline-color: #74b3ff;
}

.player-space {
    bottom: 0;
    background: #fff;
    border-top: 4px solid #f9f9f9;
    left: 0;
    position: fixed;
    right: 0;
    transition: all .1s;
    z-index: 3;
}

.deck-label {
      font-size: 1.8rem;
    margin: 0 0 .8rem;
    min-width: 48rem;
    text-align: center;
}

.main-deck {
      position: relative;
}

.main-deck ul {
    list-style: none;
    margin: 0;
    max-width: 100%;
    overflow-x: auto;
    overflow-y: hidden;
    padding: 1.6rem;
    text-align: center;
    user-select: none;
    white-space: nowrap;
    width: 100%;
}

.main-deck ul li {
    display: inline-block;
    margin: 0 .8rem;
    transition: all .1s linear;
    vertical-align: top;
    white-space: nowrap;
}
.clear-bt{
  margin-left: 1.5rem;
}
</style>