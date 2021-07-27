<template>
  <el-row type="flex" class="row-bg" justify="space-between" align="middle">
    <div class="uppermenu" style="margin-left:15px">
      <span>신상품순</span> | <span>높은 가격순</span> | <span>낮은 가격순</span> | <span>거래 시간순</span>
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
    <!-- url 알게 되면 연결 callDeals -->
    <li v-for="i in state.count" class="infinite-list-item" :key="i"  @click="dealDetail">
      <conference />
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
import { reactive } from 'vue'
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

    const state = reactive({
      count: 10
    })

    const load = function () {
      state.count += 4
    }

    const dealDetail = function () {
      router.push('detail')
    }
    return { state, load, dealDetail }
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
}
</style>
