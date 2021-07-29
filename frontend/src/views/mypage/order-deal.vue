<template>
  <h1 style="font-size:30px; text-align:left; margin-left:100px">나의 구매 목록</h1>
  <div v-if="sellList">
    <ul class="infinite-list">
    <!-- url 알게 되면 연결 callDeals -->
      <li v-for="order in info.orderList" @click="clickDeal(order.productId)" class="infinite-list-item" :key="order.productId" >
        <conference :deal="order"/>
      </li>
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :total="total">
      </el-pagination>
    </ul>
  </div>
  <div v-else>
    <b>내가 구매한 상품이 없습니다</b>
  </div>
</template>

<script>
import Conference from '@/views/home/components/conference'
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Order-deal',

  components: {
    Conference,
  },

  data() {
    //! pagination 데이터 설정
    return {
      filtered: [],
      search: '',
      page: 3,
      pageSize: 4,
      total: 20
    }
  },

  setup () {
    const store = useStore()
    const router = useRouter()
    const info = reactive({
      orderList:''
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'order-deal')
      if (store.dispatch('root/requestBuyList')){
        store.dispatch('root/requestBuyList')
        .then (res => {
          info.orderList = res.data
          console.log(info.orderList)
        })
      }
    })

    const state = reactive({
      count: 10
    })

    const load = function () {
      state.count += 4
    }

    const clickDeal = function (id) {
      router.push({
        name: 'deal-detail',
        params: {
          productId: id
        }
      })
    }

    return { state, load, clickDeal }
  }
}
</script>

<style>
.infinite-list {
  padding-left: 0;
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
  .infinite-list {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .infinite-list {
    min-width: 1021px;
  }
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}
</style>
