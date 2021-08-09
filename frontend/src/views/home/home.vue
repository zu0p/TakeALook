<template>
  <el-row type="flex" class="row-bg" justify="space-between" align="middle">
    <div class="uppermenu" style="margin-left:15px" v-if="!info.searched">
      <span @click="newDeal" v-if="info.current == 0" style="color:#58ACFA; font-weight:bold;">신상품순</span><span @click="newDeal" v-else>신상품순</span>
      | <span @click="priceHigh" v-if="info.current == 1" style="color:#58ACFA; font-weight:bold;">높은 가격순</span> <span @click="priceHigh" v-else>높은 가격순</span>
      | <span @click="priceLow" v-if="info.current == 2" style="color:#58ACFA; font-weight:bold;">낮은 가격순</span><span @click="priceLow" v-else>낮은 가격순</span>
      | <span @click="reserveTime" v-if="info.current == 3" style="color:#58ACFA; font-weight:bold;">거래 시간순</span><span @click="reserveTime" v-else>거래 시간순</span>
    </div>
    <div>
      <el-select class="category" v-model="info.value" placeholder="카테고리" style="width:140px; margin-right:10px;">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-input
        placeholder="검색할 내용을 입력하세요"
        prefix-icon="el-icon-search"
        v-model="info.search"
        @keyup.enter="searchDeal"
        style="width:300px;">
      </el-input>
      <el-button type="info" style="margin-left:10px" @click="searchDeal" v-model="info.search">검색</el-button>
    </div>
  </el-row>

  <ul class="infinite-list">
    <div v-if="!info.searched" >
      <li v-for="deal in info.dealList" @click="clickDeal(deal.productId)" class="infinite-list-item" :key="deal.productId">
        <conference :deal="deal"/>
      </li>
    </div>
    <div v-else>
      <div v-if="!info.searchResult" >
        <h2 style="margin-top:200px; text-align:center;"><i class="el-icon-warning-outline" style="margin-left:5px;"></i>
          검색하신 "{{info.search}}"에 해당하는 거래가 존재하지 않습니다</h2>
          <span style="font-size:20;">입력하신 검색어를 확인하시고 다시 검색해 주세요</span>
      </div>
      <li v-else v-for="deal in info.dealList" @click="clickDeal(deal.productId)" class="infinite-list-item" :key="deal.productId">
        <conference :deal="deal"/>
      </li>
    </div>
    <el-pagination
      background
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-size="info.pageSize"
      :total="info.total"
      :current-page="info.currentpage"
      v-if="info.searchResult"
      style=" text-align:center;">
    </el-pagination>
  </ul>

</template>

<script>
import Conference from './components/conference'
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'

export default {
  name: 'Home',

  components: {
    Conference,
  },

  watch: {
    '$route' (to, from) {
      if (to.path != from.path) {
        this.$router.go()
      }
    }
  },

  data() {
    return {
      options: [{
        value: '',
        label: '전체'
      },{
        value: 'digital',
        label: '디지털/가전'
      }, {
        value: 'furniture',
        label: '가구/인테리어'
      }, {
        value: 'fashion',
        label: '패션/잡화'
      }, {
        value: 'art',
        label: '미술품'
      }],
    }
  },

  setup () {
    const router = useRouter()
    const store = useStore()
    const route = useRoute()

    const info = reactive({
      dealList: '',
      search: null,
      searched: false,
      value: '',
      current: 0,
      currentpage:0,
      page: 0,
      // 한 페이지에 뜨는 상품 수
      pageSize: 10,
      // 총 상품 수
      total: 0,
      searchResult: true
    })

    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'home')
      if (route.path == "/high-price") {
        priceHigh()
      } else if (route.path == "/low-price") {
        priceLow()
      } else if (route.path == "/reserve-time") {
        reserveTime()
      } else if (route.path == "/"){
        newDeal()
      } else {
        if (router.currentRoute._value.params.categories == "0"){
          info.value = ''
          } else {
            info.value = router.currentRoute._value.params.categories
          } if (router.currentRoute._value.params.search == "0"){
            info.search = ''
          } else {
            info.search = router.currentRoute._value.params.search
          }
          info.searched = true
          searchDeal()
        }
    })

    const handleCurrentChange = function (e) {
      info.page = e-1
      // 신상품순
      if (info.current == 0) {
        store.dispatch('root/requestDealList', {page: info.page, size:9})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.dealList = res.data.content
            }
          })
      } else if (info.current == 1) { // 높은 가격순
          store.dispatch('root/requestPriceHigh', {page: info.page, size:9})
            .then (res => {
              if (res.data.statusCode != 404) {
                info.dealList = res.data.content
              }
            })
      } else if (info.current == 2) { // 낮은 가격순
          store.dispatch('root/requestPriceLow', {page: info.page, size:9})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.dealList = res.data.content
            }
          })
      } else if (info.current == 3) { // 거래 시간순
          store.dispatch('root/requestReserveTime', {page: info.page, size:9})
            .then (res => {
              if (res.data.statusCode != 404) {
                info.dealList = res.data.content
              }
            })
      } else if (info.current == 4) { // 검색 중인 경우
          store.dispatch('root/requestSearch', {categories: info.value, keyword: info.search, page:info.page, size:9})

      }
    }

    const clickDeal = function (id) {
      router.push({
        name: 'deal-detail',
        params: {
          productId: id
        }
      })
    }

    const searchDeal = function () {
      info.current = 4
      if (!info.search && !info.value) {
        info.searched = false
        info.current = 0
        store.dispatch('root/requestDealList', {page: 0, size: 9})
          .then (res => {
            if (res.data.statusCode != 404){
              info.total = res.data.totalElements
              info.pageSize = res.data.size
              info.dealList = res.data.content
              router.push('/')
            }
          })
      } else {
        store.dispatch('root/requestSearch', {categories: info.value, keyword: info.search, page:0, size:9})
        .then(res=> {
          info.searched = true
          if (res.data.statusCode != 404){
            info.total = res.data.totalElements
            info.pageSize = res.data.size
            info.dealList = res.data.content
            if (info.value && info.search) {
              router.push({
                name: 'search',
                params: {
                categories: info.value,
                search: info.search
                }
              })
            } else if (info.value){
              router.push({
                name: 'search',
                params: {
                categories: info.value,
                search: 0
                }
              })
            } else {
              router.push({
                name: 'search',
                params: {
                categories: 0,
                search: info.search
                }
              })
            }
          } else {
            info.searchResult = false
            if (info.value && info.search) {
              router.push({
                name: 'search',
                params: {
                categories: info.value,
                search: info.search
                }
              })
            } else if (info.value){
              router.push({
                name: 'search',
                params: {
                categories: info.value,
                search: 0
                }
              })
            } else {
              router.push({
                name: 'search',
                params: {
                categories: 0,
                search: info.search
                }
              })
            }
          }
        })
      }
    }

    const priceHigh = function () {
      info.current = 1
        store.dispatch('root/requestPriceHigh', {page: 0, size: 9})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.total = res.data.totalElements
              info.pageSize = res.data.size
              info.dealList = res.data.content
            }
          })
        router.push({
          name: "price-high"
        })
    }

    const newDeal = function () {
      info.current = 0
        store.dispatch('root/requestDealList', {page: 0, size: 9})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.total = res.data.totalElements
              info.pageSize = res.data.size
              info.dealList = res.data.content
            }
          })
      router.push({
        name: "home"
      })
    }

    const priceLow = function () {
      info.current = 2
        store.dispatch('root/requestPriceLow', {page: 0, size: 9})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.total = res.data.totalElements
              info.pageSize = res.data.size
              info.dealList = res.data.content
            }
          })
      router.push({
        name: "price-low"
      })
    }

    const reserveTime = function () {
      info.current = 3
        store.dispatch('root/requestReserveTime', {page: 0, size: 9})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.total = res.data.totalElements
              info.pageSize = res.data.size
              info.dealList = res.data.content
            }
          })
      router.push({
        name: "reserve-time"
      })
    }

    return { info, clickDeal, searchDeal, handleCurrentChange, priceHigh, newDeal, priceLow, reserveTime }
  }
}
</script>

<style scoped>
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

.infinite-list {
  text-align: center;
}


.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}

.create-deal {
  float: right;
}

.uppermenu span:hover{
  color: #58ACFA;
  pointer-events: stroke;
}
</style>
