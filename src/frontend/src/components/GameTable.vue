<template>
    <div class="game-table">
        <div class="inside-table">
            <div class="many-users">
                <div></div>
                <div class="top"><button @click="getResults">Get results</button></div>
                <div></div>
                <div class="left"></div>
                <div class="tabelunia"><div class="content-table">
                    <div>player1</div>
                    <planning-poker-card :v-if="isSelectionConfirmed" :estimation="mainPlayerCard"/>
                    <div>player2</div>
                    <planning-poker-card :v-if="showResults" :estimation="playerTwo"/>
                    <div>player3</div>
                    <planning-poker-card :v-if="showResults" :estimation="playerThree"/>
                    <div>player4</div>
                    <planning-poker-card :v-if="showResults" :estimation="playerFour"/>
                </div></div>
                <div class="right"></div>
                <div></div>
                <div class="bot"><div class="bot-player"><div class="bot-player2"><div class="card-bot"></div></div></div></div>
                <div></div>
            </div>
        </div>
    </div>
</template>

<script>
import PlanningPokerCard from './PlanningPokerCard.vue';

export default {
    name: 'GameTable',
    components: {
        PlanningPokerCard
    },
    props: {
        mainPlayerCard: String,
        isSelectionConfirmed: Boolean
    },
    data() {
        return {
            fibonacci: ['0', '1', '2', '3', '5', '8', '13', '21', '34', '55', '89', '?'],
            showResults: false,
            playerTwo: '',
            playerThree: '',
            playerFour: ''
        }
    },
    methods: {
        getResults() {
            this.showResults = true;
            this.playerTwo = this.fibonacci[Math.floor(Math.random() * (11))];
            this.playerThree = this.fibonacci[Math.floor(Math.random() * (11))];
            this.playerFour = this.fibonacci[Math.floor(Math.random() * (11))];
        }
    }
}
</script>


<style>
.game-table {
    align-items: center;
    display: flex;
    justify-content: center;
    max-width: 100vw;
}

.inside-table {
    align-items: center;
    display: flex;
    justify-content: center;
    margin: auto;
    padding-bottom: 0;
    width: 100%;
}

.many-users {
    grid-gap: .8rem;
    display: inline-grid;
    grid-template-areas: "left top right" "left table right" "left bottom right";
    grid-template-columns: 10.4rem 1fr 10.4rem;
    grid-template-rows: auto 1fr auto;
    margin: 0 auto;
    min-height: 200px;
    width: auto;
}

.user-lonely {
    grid-template-rows: 8rem 1fr 13.4rem;
}

.top {
    align-items: stretch;
    grid-area: top;
    z-index: 2;
    /* align-items: flex-end; */
    padding-bottom: .8rem;
    /* align-items: center; */
    display: flex;
    justify-content: center;
}
.left {
    grid-area: left;
    flex-direction: column-reverse;
    z-index: 3;
    padding: 8rem 0;
    align-items: center;
    display: flex;
    justify-content: center;
}

.tabelunia {
    align-items: center;
    display: flex;
    justify-content: center;
    background: #f0a7cc;
    border-radius: 2.8rem;
    grid-area: table;
    height: auto;
    margin: 0 auto;
    min-height: 15.1rem;
    min-width: 33.8rem;
    padding: 0 1.6rem;
    position: relative;
    width: 100%;
}
.content-table {
    align-items: center;
    border-radius: 16px;
    bottom: 2.4rem;
    display: flex;
    justify-content: center;
    left: 2.4rem;
    position: absolute;
    right: 0.4rem;
    top: 2.4rem;
}
.right{
    grid-area: right;
    padding: 8rem 0;
    flex-direction: column-reverse;
    z-index: 3;
    align-items: center;
    display: flex;
    justify-content: center;
}
.bot {
    align-items: stretch;
    grid-area: bottom;
    z-index: 2;
    padding: 1.2rem 0 .4rem;
    display: flex;
    justify-content: center;
}
.bot-player {
    max-width: 10.4rem;
    width: 10.4rem;
}
.bot-player-2 {
    align-items: center;
    display: flex;
    flex-direction: column;
    height: 100%;
    justify-content: space-evenly;
    max-width: 100%;
    perspective: 10rem;
    perspective-origin: bottom;
}

.card-bot {
    border-radius: .8rem;
    height: 7rem;
    width: 4rem;
    flex-shrink: 0;
}
</style>