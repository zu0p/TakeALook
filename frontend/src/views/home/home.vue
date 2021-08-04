<template>
  <el-row type="flex" class="row-bg" justify="space-between" align="middle">
    <div class="uppermenu" style="margin-left:15px">
      <span @click="newDeal" v-if="info.current == 0" style="color:#ffd04b">신상품순</span><span @click="newDeal" v-else>신상품순</span>
      | <span @click="priceHigh" v-if="info.current == 1" style="color:#ffd04b">높은 가격순</span> <span @click="priceHigh" v-else>높은 가격순</span>
      | <span @click="priceLow" v-if="info.current == 2" style="color:#ffd04b">낮은 가격순</span><span @click="priceLow" v-else>낮은 가격순</span>
      | <span @click="reserveTime" v-if="info.current == 3" style="color:#ffd04b">거래 시간순</span><span @click="reserveTime" v-else>거래 시간순</span>
    </div>
    <div >
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
    <li v-for="deal in info.dealList" @click="clickDeal(deal.productId)" class="infinite-list-item" :key="deal.productId" >
      <conference :deal="deal"/>
    </li>
    <h2 v-if="!info.searchResult" style="margin-top:200px; margin-bottom:200px; text-align:center;">검색어에 해당하는 거래가 존재하지 않습니다</h2>
    <div style="text-align: end">
      <el-button type="info" style="margin-right:100px" @click="createDeal">거래 생성</el-button>
    </div>
    <el-pagination
      background
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-size="info.pageSize"
      :total="info.total"
      v-if="info.searchResult"
      style=" text-align:center;">
    </el-pagination>
  </ul>

</template>

<script>
import Conference from './components/conference'
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Home',

  components: {
    Conference,
  },
  data() {
    //! pagination 데이터 설정
    return {
      options: [{
        value: '',
        label: '전체'
      },{
        value: '디지털/가전',
        label: '디지털/가전'
      }, {
        value: '가구/인테리어',
        label: '가구/인테리어'
      }, {
        value: '여성패션/잡화',
        label: '여성패션/잡화'
      }, {
        value: '남성패션/잡화',
        label: '남성패션/잡화'
      }, {
        value: '뷰티/미용',
        label: '뷰티/미용'
      }, {
        value: '미술품',
        label: '미술품'
      }],
    }
  },

  setup () {
    const router = useRouter()
    const store = useStore()

    const info = reactive({
      dealList: '',
      search: '',
      value: '',
      current: 0,
      page: 0,
      // 한 페이지에 뜨는 상품 수
      pageSize: 10,
      // 페이지네이션 총 페이지
      total: 0,
      searchResult: true
    })

    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'home')
      store.dispatch('root/requestDealList', {page: 0, size: 10})
        .then (res => {
          info.total = res.data.totalElements
          if (res.data.statusCode != 404) {
            info.dealList = res.data.content
          }
        })

    })

    const handleCurrentChange = function (e) {
      info.page = e-1
      console.log(info.page)

      // 신상품순
      if (info.current == 0) {
        store.dispatch('root/requestDealList', {page: info.page, size: 10})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.dealList = res.data.content
            }
          })
      } if (info.current == 1) { // 높은 가격순
          store.dispatch('root/requestPriceHigh', {page: info.page, size: 10})
            .then (res => {
              if (res.data.statusCode != 404) {
                info.dealList = res.data.content
              }
            })
      } if (info.current == 2) { // 낮은 가격순
          store.dispatch('root/requestPriceLow', {page: info.page, size: 10})
          .then (res => {
            if (res.data.statusCode != 404) {
              info.dealList = res.data.content
            }
          })
      } if (info.current == 3) { // 거래 시간순
          store.dispatch('root/requestReserveTime', {page: info.page, size: 10})
            .then (res => {
              if (res.data.statusCode != 404) {
                info.dealList = res.data.content
              }
            })
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
      store.dispatch('root/requestDealList',)
        .then (res => {
          if (res.data.statusCode != 404) {
            info.dealList = []
            for(var deal in res.data) {
              if (!info.search && info.value) {
                if (info.value === res.data[deal].categories) {
                    info.dealList.push(res.data[deal])
                  }
              } else if (res.data[deal].productName.includes(info.search)){
                if (info.value) {
                  if (info.value === res.data[deal].categories) {
                    info.dealList.push(res.data[deal])
                  }
                } else {
                  info.dealList.push(res.data[deal])
                }
              }
            }
            if (!info.dealList.length) {
              info.searchResult = false
            }
          }
        })
    }

    const priceHigh = function () {
      info.current = 1
      handleCurrentChange()
    }

    const newDeal = function () {
      info.current = 0
      handleCurrentChange()
    }

    const priceLow = function () {
      info.current = 2
      handleCurrentChange()
    }

    const reserveTime = function () {
      info.current = 3
      handleCurrentChange()
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
  text-align: left;
  margin-left: 4%;
  margin-right: auto;
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
  color: #ffd04b;
  pointer-events: stroke;
}
</style>
