<template>
<div>
  <h3 class="header">Check your planning history</h3>
<!--  lista pokoi-->
  <ul class="list-group w-75  " >
    <li class="list-group-item  itList  "
        v-for="(game,index) in userGames" :key="game.id">
      <div class=" ms-4 me-auto" data-bs-toggle="collapse"  :data-bs-target="'#collapseExample'+index">
        <div class="fw-bold">
         {{ game.name     }}<div class="badge ms-2 bg-info rounded-pill">Gimmie more</div>
        </div>

      </div>
      <!--     lista storiesów -->
      <div class="collapse" :id="'collapseExample'+index">
      <ul class="list-group   w-100 p-1">
        <li class="list-group-item  itList "
            v-for="(story) in game.stories" :key="story.id">
          <div class="ms-4 me-auto">
          <h5 class="fw-bold">{{ story.description }}

            <div class="badge bg-primary rounded-pill">{{story.estimation}}</div></h5>


          </div>
          <!--     lista tasków -->
          <ul class="list-group list-group-flush justify-content-start w-100">
            <li class="list-group-item itList "
                v-for="(task) in story.tasks" :key="task.id">
              <div class="ms-4 me-auto">
              {{ task.description }}


              </div>
            </li>
          </ul>
        </li>
      </ul>
      </div>
    </li>
  </ul>
</div>
</template>

<script>
export default {
  name: "HistoryView",
  data() {
    return {
      userGames: [{
        name: "gra1",
        id: "1",
        stories: [
          {
            id: "1",
            description: "story1",
            estimation: "8",
            tasks: [
              {
                id: "1",
                description: "tasks1"
              },
              {
                id: "1",
                description: "tasks1"
              },
              {
                id: "1",
                description: "tasks1"
              }
            ]
          },
          {
            id: "1",
            description: "story1",
            estimation: "8",
            tasks: [
              {
                id: "1",
                description: "tasks1"
              },
              {
                id: "1",
                description: "tasks1"
              },
              {
                id: "1",
                description: "tasks1"
              }
            ]
          }
        ]
      },
        {
          name: "gra1",
          id: "2",
          stories: [
            {
              id: "1",
              description: "story1",
              estimation: "8",
              tasks: [
                {
                  id: "1",
                  description: "tasks1"
                },
                {
                  id: "1",
                  description: "tasks1"
                },
                {
                  id: "1",
                  description: "tasks1"
                }
              ]
            },
            {
              id: "1",
              description: "story1",
              estimation: "8",
              tasks: [
                {
                  id: "1",
                  description: "tasks1"
                },
                {
                  id: "1",
                  description: "tasks1"
                },
                {
                  id: "1",
                  description: "tasks1"
                }
              ]
            }
          ]
        }

      ]
    }
  }
}

</script>

<style scoped>
.header{
  text-align: left;
  margin-left: 5%;
}

.itList{
  /*justify-content: flex-start;*/
  text-align: left;
  /*alignment: left;*/
  /*display: inline-block;*/
      /*inline-grid;*/

}
ul {
  display: table;
  margin: 0 auto;
}
</style>