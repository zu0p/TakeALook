<template>
  <li v-for="deal in dealList" @click="clickDeal(deal.productId)" class="infinite-list-item" :key="deal.productId" >
    <conference :deal="deal"/>
  </li>
</template>

<script>
import Conference from './conference'
import { reactive } from '@vue/reactivity'
import { useRouter, useRoute } from 'vue-router'
import { onBeforeMount, onMounted, onUpdated } from '@vue/runtime-core'
import { useStore } from 'vuex'

export default {
  name: 'price-low',

  components: {
    Conference,
  },

  props: ["dealList"],

  setup (props) {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()

    const clickDeal = function (id) {
      router.push({
        name: 'deal-detail',
        params: {
          productId: id
        }
      })
    }

    return {clickDeal}
  }
}
</script>

<style>

</style>
