<template>
  <el-card>
    <template #header>
      <span> 가격 제안하기 </span>
    </template>
    <el-form label-position="leftPosition">
      <el-form-item label="현재가">
        <span>{{state.curPrice}}</span>
      </el-form-item>
      <el-form-item label="낙찰 카운트다운">
        <span>{{state.count}}</span>
      </el-form-item>
      <el-form-item>
        <el-input-number v-model="state.proposePrice" @change="handleChange" :step="state.gap"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button @click="countDown">카운트 시작</el-button>
        <el-button @click="resetCount" >카운트 리셋</el-button>
        <el-button @click="propose">가격 제안</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  name: 'propose-form',
  setup(props, {emit}){
    const state = reactive({
      curPrice: 0,
      proposePrice: 0,
      gap: 10,
      count: 30
    });

    const countDown = function(){
      if(state.count == 0){
        alert("낙찰!!")
      }
      if(state.count>0){
        setTimeout(()=>{
          state.count-=1
          countDown()
        }, 1000)
      }
    }

    const resetCount = function(){
      state.count = 30
    }

    const handleChange = function(value) {
      console.log(value);
    }

    const propose = function(){
      state.curPrice = state.proposePrice
    }

    return {state, handleChange, countDown, resetCount, propose}
  }
}
</script>

<style>

</style>
