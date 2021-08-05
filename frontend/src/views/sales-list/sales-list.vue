<template>
  <el-container>
    나의 판매 목록
  </el-container>
</template>

<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'SalesList',
  setup () {
    const store = useStore()

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'sales-list')
    })
  }
}
</script>
