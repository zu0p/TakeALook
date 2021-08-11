<template>
  <h1 style="font-size:30px; text-align:left; margin-left:100px">나의 판매 목록</h1>
  <div v-if="info.sellList">
    <ul class="infinite-list">
      <li v-for="sell in info.sellList" @click="clickDeal(sell.productId)" class="infinite-list-item" :key="sell.productId" >
        <conference :deal="sell"/>
      </li>
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="info.pageSize"
        :total="info.total">
      </el-pagination>
    </ul>
  </div>
  <div v-else>
    <h2 style="margin-top:200px; text-align:center;"><i class="el-icon-warning-outline" style="margin-left:5px;"></i>
        등록한 거래가 없습니다</h2>
    <span style="font-size:20;">지금 바로 거래를 등록해 보세요</span>
  </div>
</template>

<script>
import Conference from '@/views/home/components/conference'
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'My-deal',

  components: {
    Conference,
  },

  setup () {
    const router = useRouter()
    const store = useStore()
    const info = reactive({
      sellList:'',
      pageSize: 10,
      total: 0
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'my-deal')
      store.dispatch('root/requestSellList', {page:0, size:info.pageSize})
        .then (res => {
          if (res.data.statusCode != 404) {
            info.sellList = res.data.content
            info.total = res.data.totalElements
            info.pageSize = res.data.size
          }
        })
    })

    const handleCurrentChange = function (e) {
      info.page = e-1
      store.dispatch('root/requestSellList', {page:info.page, size:info.pageSize})
        .then(res => {
          if (res.data.statusCode != 404) {
            info.sellList = res.data.content
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

    return { info, clickDeal, handleCurrentChange }
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
