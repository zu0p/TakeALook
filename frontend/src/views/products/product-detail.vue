<template>
  {{ $route.params.productId + '번 방 상세 보기 페이지' }}
  <el-form-item>
    본문
  </el-form-item>
  <el-form-item>
  <el-button type="warning" @click="clickUpdate">수정</el-button>
  </el-form-item>
</template>
<style>
</style>
<script>
import { reactive, onBeforeMount, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'

export default {
  name: 'product-detail',
  props: {
    productId: [ String||Number ]
  },
  setup (props) {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      productId: props.productId,
      loading: false,
    })

  // 수정 클릭 시 validate 체크 후 그 결과 값에 따라, 게시글 수정 API 호출 또는 경고창 표시
  const clickUpdate = function () {
    console.log(state.productId)
    router.push({
      name: 'update-deal-form',
      params: {
        productId: state.productId,
      }
    })
  }

    // 페이지 진입시 불리는 훅
    onBeforeMount(() => {
      // console.log(state.productId)
      // console.log(route.params.productId)
      // console.log(state.productId)
      store.commit('root/setMenuActiveMenuName', 'home')
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.productId = ''
    })

    return { state, clickUpdate }
  }
}
</script>
