<template>
    <div class="player-space">
        <h2>Choose your card...</h2>
        <section class="deck">
            <PlanningPokerCard v-for="fibb in fibonacci" :key="`card-${fibb}`" :estimation="fibb"
                                @select="selectCard(fibb)" :isSelected="selectedCard === fibb"/>
        </section>
    </div>
</template>

<script>
import PlanningPokerCard from './PlanningPokerCard.vue';

export default {
    name: 'DeckOfCards',
    components: {
        PlanningPokerCard
    },
    data() {
        return {
            fibonacci: ['0', '1', '2', '3', '5', '8', '13', '21', '34', '55', '89', '?'],
            selectedCard: ''
        }
    },
    methods: {
        selectCard(fibb) {
            this.selectedCard = fibb
        }
    }
}
</script>

<style>
.player-space {
    position: absolute;
    width: 100%;
}
.deck {
    display: grid;
    grid-template-columns: repeat(12, 100px);
    grid-column-gap: 12px;
    justify-content: center;
}
</style>