<template>
  <el-row style="text-align:left; width:250px" @click="dealDetail">
    <img :src="deal.imageUrl" alt="" style="width:100px; height:100px;">
    <div style="margin-left:20px;">
      <p style="font-weight: bold;">{{ deal.productName }}</p>
      <p>{{ deal.basePrice }}원</p>
    </div>
  </el-row>
</template>

<script>
export default {
  props: ["deal"],
}
</script>

<style>

</style>
