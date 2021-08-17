<template>
  <el-row style="margin-top:10px; margin-bottom:10px; height: 70px;">
    <img :src="info.imageUrl" alt="" style="width:50px; height:50px; border-radius: 7px; align-self: center">
    <div style="margin-left:20px; text-align:left; align-self: center">
      <p style="font-weight: bold; margin-top:0;">{{ chat.productName }}
        <span style="margin-bottom:0; font-size:10px;">{{ chat.receiver }}</span></p>
      <p style="margin-bottom:0;">최근 대화</p>
    </div>
  </el-row>
  <hr color="#F2F2F2" size="1px">
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'

export default {
  props: ["chat"],

  setup(props){
    const store = useStore()
    const info = reactive({
      imageUrl: `require('@/assets/pimages/${props.chat.productId}.jpg')`
    })
    // store.dispatch('root/requestDealDetail', props.chat.productId)
    // .then(res=> {
    //   info.imageUrl = res.data.imageUrl
    // })
    return {info}
  }
}
</script>

<style>
</style>
