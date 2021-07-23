<template>
  <!-- 상품 명 -->
  <el-container>
    <span class="article-title">제목 : </span>
    <el-input
      class="product-input"
      placeholder="제목을 입력해주세요"
      autosize
      v-model="product"
    ></el-input>
  </el-container>

  <hr>

  <el-container class="article-content">
    <div class="product-picture">
      <el-upload
        class="avatar-uploader"
        action="https://jsonplaceholder.typicode.com/posts/"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <img v-if="imageUrl" :src="imageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </div>
    <div class="product-form">
      <div class="seller">
        <span>판매자: 모르는 개 산책 </span>
      </div>
      <!-- 카테고리 선택 (여러개 가능) -->
      <div class="category">
        <span>카테고리: </span>
        <el-select v-model="category" placeholder="Select">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <!-- 기준가 -->
      <div class="base-price">
        <span>기준가: </span>
        <el-input
          v-model="base_price"
          @keypress="isNumber($event)"
          type="text"
          class="product-input"
          placeholder="가격을 입력해주세요(단위: 원)"
          maxlength="13"
          autosize
        ></el-input>
      </div>
      <!-- 거래시간 -->
      <div class="reserve-date">
        <span>거래시간 및 일자: </span>
        <el-date-picker
          v-model="reserve_date"
          type="datetime"
          placeholder="날짜 및 시간 선택"
        >
        </el-date-picker>
      </div>
      <!-- 상세설명 -->
      <div class="product-description">
        <span>상세설명: </span>
        <el-input
          v-model="desc"
          type="textarea"
          placeholder="내용을 입력해주세요"
          maxlength="300"
          show-word-limit
          :autosize="{ minRows: 10, maxRows: 10 }"
        >
        </el-input>
      </div>
    </div>
  </el-container>

  <hr>

  <div class="button-wrapper">
    <el-button type="danger" @click="clickCancel">
      <span>취소</span>
    </el-button>
    <el-button type="primary" @click="clickCreate">
      <span>작성</span>
    </el-button>
  </div>
</template>

<script>
import { onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'Createdealform',
  setup () {
    const store = useStore()


    // 페이지 진입시 불리는 훅
    onMounted (() => {
      store.commit('root/setMenuActiveMenuName', 'create-deal-form')
    })
  },

  // 썸네일 사진 등록 로직
  data() {

    return {
      imageUrl: '',
      options: [{
        value: '없음',
        label: '없음'
      }, {
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
      }],
      product: '',
      category: [],
      base_price: '',
      reserve_date: [],
      desc: '',
      pickerOptions: {
        shortcuts: [{
          text: 'Today',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }]
      },
      value1: '',
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    isNumber: function(evt) {
      evt = (evt) ? evt : window.event;
      var charCode = (evt.which) ? evt.which : evt.keyCode;
      if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
        evt.preventDefault();;
      } else {
        return true;
      }
    },
  }
}
</script>
<style>
  .product-input {
    min-width: 250px;
    width: 250px;
    height: 50%;
    margin-left: 1%;
    line-height: 50px;
  }
  .article-title {
    min-width: 75px;
    font-size: 2rem;
  }
  .article-content {
    justify-content: center;
  }
  .article-content .product-picture {

  }
  .article-content .product-form {
    margin-left: 50px;
    min-width: 500px;
    text-align: left;
  }
  .article-content .product-form .seller {
    margin-top: 1%;
    min-height: 60px;
  }
  .article-content .product-form .category {
    margin-top: 1%;
    min-height: 60px;
  }
  .article-content .product-form .base-price {
    margin-top: 1%;
    min-height: 60px;
  }
  .article-content .product-form .reserve-date {
    margin-top: 1%;
    min-height: 60px;
  }
  .article-content .product-form .product-description {
    min-width: 100px;
    width: 500px;
    min-height: 250px;
    max-height: 250px;
    resize: none;
  }
  .button-wrapper {

  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    min-width: 500px;
    min-height: 500px;
    line-height: 500px;
    text-align: center;
  }
  .avatar {
    width: 500px;
    height: 500px;
    display: block;
  }
</style>
