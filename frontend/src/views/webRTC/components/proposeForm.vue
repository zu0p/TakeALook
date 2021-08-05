<template>
  <el-form label-position="leftPosition">
    <el-form-item label="현재가">
      <span>{{state.curPrice}}</span>
    </el-form-item>
    <el-form-item label="낙찰 카운트다운">
      <span>{{state.count}}</span>
    </el-form-item>
    <el-form-item>
    </el-form-item>
    <el-form-item>
      <el-button >가격 제안</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { reactive } from '@vue/reactivity';
export default {
  name: 'propose-form',
  setup(props, {emit}){
    const state = reactive({
      curPrice: 0,
      propsePrice: 0,
      gap: 0,
      count: 60
    });
    return {state}
  }
}
</script>

<style>

</style>
