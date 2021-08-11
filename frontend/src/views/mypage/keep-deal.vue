<template>
  <h1 style="font-size:30px; text-align:left; margin-left:100px" v-loading.fullscreen.lock="state.isLoading">나의 관심 목록</h1>
  <div v-if="info.wishList">
    <ul class="infinite-list">
      <li v-for="wish in info.wishList" @click="clickDeal(wish.productId)" class="infinite-list-item" :key="wish.productId" >
        <conference :deal="wish" @buyerJoin="buyerJoin"/>
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
    <div v-if="!info.searchResult" >
      <h2 style="margin-top:200px; text-align:center;"><i class="el-icon-warning-outline" style="margin-left:5px;"></i>
        찜한 거래가 없습니다</h2>
      <span style="font-size:20;">이런 상품은 어떠세요?</span>
      <el-row justify="center" style="margin-top:10px">
        <ul v-for="deal in info.recommend" :key="deal.productId" @click="dealDetail(deal.productId)">
          <recommend :deal="deal"/>
        </ul>
      </el-row>
    </div>
  </div>
</template>

<script>
import Conference from '@/views/home/components/conference'
import Recommend from '../deal-detail/components/recommend'
import { onBeforeMount, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Keep-deal',

  components: {
    Conference,
    Recommend,
  },

  setup () {
    const store = useStore()
    const router = useRouter()
    const info = reactive({
      wishList:'',
      pageSize: 10,
      total: 0,
      recommend: ''
    })

    const state = reactive({
      isLoading: true,
      name: ''
    })

    onBeforeMount(()=>{
      store.dispatch('root/requestUserInfo')
        .then(res=>{
          state.name = res.data.userId
      })
    })

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'keep-deal')
      store.dispatch('root/requestWishList', {page:0, size:info.pageSize})
          .then (res => {
            info.wishList = res.data.content
            info.total = res.data.totalElements
            info.pageSize = res.data.size
          })
          .catch(err => {
            store.dispatch('root/requestAllDeal')
            .then (res => {
              if (res.data.statusCode != 404) {
                var temp = res.data.sort(()=>Math.random()-0.5)
                info.recommend = []
                for (var deal in temp) {
                  if (info.recommend.length < 4){
                    info.recommend.push(temp[deal])
                  }
                }
                console.log(info.recommend)
              }
            })
          })
        state.isLoading = false
    })

    const handleCurrentChange = function (e) {
      info.page = e-1
      store.dispatch('root/requestWishList', {page:info.page, size:info.pageSize})
        .then(res => {
          if (res.data.statusCode != 404) {
            info.wishList = res.data.content
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

    const buyerJoin = function(pid, price){
      const req = {
        userId: state.name,
        productId: pid
      }
      console.log(req)

      // 서버에 요청보내서 res의 room != none이면 입장
      store.dispatch('root/requestJoinTrade', req)
        .then(res=>{
          let isActive = res.data.room
          console.log(isActive)
          if(isActive!='none'){
            // 거래 세션에 입장
            router.push({
              name: 'meeting-detail',
              params: {
                meetingId: isActive,
                userId: state.name,
                isSeller: 0,
                basePrice: price
              },
            })
          }
          else{
            alert("거래 세션이 아직 생성되지 않아 입장할 수 없습니다.")
          }
        })
    }

    return { info, state, clickDeal, handleCurrentChange, buyerJoin }
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
