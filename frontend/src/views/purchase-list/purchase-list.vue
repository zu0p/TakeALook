<template>
  <el-container>
    나의 구매 목록
  </el-container>
</template>

<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'PurchaseList',
  setup () {
    const store = useStore()

    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'purchase-list')
    })
  }
}
</script>
