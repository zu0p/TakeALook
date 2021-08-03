<template>
  <h1 style="font-size:30px; text-align:left; margin-left:100px">나의 관심 목록</h1>
  <div v-if="info.wishList">
    <ul class="infinite-list">
      <li v-for="wish in info.wishList" @click="clickDeal(wish.productId)" class="infinite-list-item" :key="wish.productId" >
        <conference :deal="wish"/>
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
    <b>찜한 거래가 없습니다</b>
  </div>
</template>

<script>
import Conference from '@/views/home/components/conference'
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Keep-deal',

  components: {
    Conference,
  },

  data() {
    //! pagination 데이터 설정
    return {
      deals: null,
      filtered: [],
      search: '',
      page: 3,
      pageSize: 4,
      total: 20
    }
  },

  setup () {
    const store = useStore()
    const info = reactive({
      wishList:'',
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'keep-deal')
      if (store.dispatch('root/requestWishList')){
        store.dispatch('root/requestWishList')
          .then (res => {
            info.wishList = res.data
          })
      }
    })

    const router = useRouter()

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

    // return { state, load, clickConference }
    return { info, state, load, clickDeal }
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
