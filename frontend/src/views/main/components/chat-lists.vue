<template>
  <el-row style="margin-top:10px; margin-bottom:10px; height: 70px;">
    <img :src="info.imageUrl" alt="" style="width:50px; height:50px; border-radius: 7px; align-self: center">
    <div style="margin-left:20px; text-align:left; align-self: center">
      <p style="margin-top:0;">{{ chat.productName }}
        <span style="margin-bottom:0; font-size:10px;">{{ chat.receiver }}</span></p>
      <p v-if="info.last" style="margin-bottom:0;">{{ info.last }}</p>
      <p v-else style="margin-bottom:0;"></p>
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
      imageUrl: require(`@/assets/pimages/${props.chat.productId}.jpg`),
      last: '',
    })

    store.dispatch('root/requestDealDetail', props.chat.productId)
    .then(res=> {
      info.imageUrl = res.data.imageUrl
    })

    store.dispatch('root/requestChatData', props.chat.roomId)
    .then(res=>{
      if (res.data.statusCode != 404) {
        let lastData = res.data.length -1
        info.last = res.data[lastData].message
      } else {
        info.last = false
      }
    })

    return {info}
  }
}
</script>

<style>
</style>
