<template>
  {{ $route.params.productId + '번 방 상세 보기 페이지' }}
</template>
<style>
</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'

export default {
  name: 'conference-detail',

  setup () {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      productId: ''
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.productId = route.params.productId
      store.commit('root/setMenuActiveMenuName', 'home')
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.productId = ''
    })

    return { state }
  }
}
</script>
