<template>
  <h1 style="font-size:30px; text-align:left; margin-left:100px">나의 구매 목록</h1>
  <div v-if="buyList">
    <ul class="infinite-list">
    <!-- url 알게 되면 연결 callDeals -->
      <li v-for="buy in info.buyList" @click="clickDeal(buy.productId)" class="infinite-list-item" :key="buy.productId" >
        <conference :deal="buy"/>
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

  setup () {
    const store = useStore()
    const router = useRouter()
    const info = reactive({
      buyList:'',
      pageSize: 10,
      total: 0
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'order-deal')
      store.dispatch('root/requestBuyList', {page:0, size:9})
        .then (res => {
          info.buyList = res.data.content
          info.total = res.data.totalElements
          info.pageSize = res.data.size
        })
    })

    const handleCurrentChange = function (e) {
      info.page = e-1
      store.dispatch('root/requestBuyList', {page:info.page, size:9})
        .then(res => {
          if (res.data.statusCode != 404) {
            info.nutList = res.data.content
          }
        })
    }

    const clickDeal = function (id) {
      router.push({
        name: 'deal-detail',
        params: {
          productId: id
        }
      })
    }

    return { clickDeal, handleCurrentChange }
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
