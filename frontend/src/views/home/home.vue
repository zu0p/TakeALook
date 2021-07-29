<template>
  <el-row type="flex" class="row-bg" justify="space-between" align="middle">
    <div class="uppermenu" style="margin-left:15px">
      <span>신상품순</span> | <span @click="priceHigh">높은 가격순</span> | <span>낮은 가격순</span> | <span>거래 시간순</span>
    </div>
    <div >
    <el-select class="category" v-model="value" placeholder="카테고리" style="width:120px; margin-right:10px">
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
      v-model="state.searchValue"
      @keyup.enter="searchDeal"
      style="width:300px;">
    </el-input>
    <el-button type="info" style="margin-left:10px" @click="searchDeal">검색</el-button>
    </div>
  </el-row>

  <ul class="infinite-list">
    <li v-for="deal in info.dealList" @click="clickDeal(deal.productId)" class="infinite-list-item" :key="deal.productId" >
      <conference :deal="deal"/>
    </li>
    <div style="text-align: end">
      <el-button type="info" style="margin-right:100px" @click="createDeal">거래 생성</el-button>
    </div>
    <el-pagination
      background
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :page-size="pageSize"
      :total="total">
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
        value: 'Option1',
        label: 'Option1'
      }, {
        value: 'Option2',
        label: 'Option2'
      }, {
        value: 'Option3',
        label: 'Option3'
      }, {
        value: 'Option4',
        label: 'Option4'
      }, {
        value: 'Option5',
        label: 'Option5'
      }],
      value: '',
      filtered: [],
      search: '',
      page: 3,
      pageSize: 4,
      total: 20,
    }
  },

  setup () {
    const router = useRouter()
    const store = useStore()

    const state = reactive({
      count: 10
    })

    const info = reactive({
      dealList: '',
    })

    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'home')
      store.dispatch('root/requestDealList')
        .then (res => {
          if (res.data.statusCode != 404) {
            info.dealList = res.data
          }
        })
    })

    const clickDeal = function (id) {
      router.push({
        name: 'deal-detail',
        params: {
          productId: id
        }
      })
    }

    const priceHigh = function () {
      store.dispatch('root/requestDealList')
        .then (res => {
          if (res.data.statusCode != 404) {
            // ! 가격 순으로 정렬
            info.dealList = res.data
            for (var deal of info.dealList) {
              console.log(deal)
            }
          }
        })
    }

    return { info, state, clickDeal, priceHigh }
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
