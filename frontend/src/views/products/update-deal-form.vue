<template>
  <!-- 사진 업로드 -->
  <el-container class="update-deal-form">
    <el-upload
      class="avatar-uploader"
      action="https://jsonplaceholder.typicode.com/posts/"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload">
      <img v-if="state.src.imageUrl" :src="state.src.imageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>

    <!-- 거래 작성 폼 -->
    <el-form v-if="!state.loading" v-model="state.form" :rules="state.rules" ref="updateDealForm" :label-position="state.form.align">
      <!-- 게시글 제목 -->
      <el-form-item prop="productName" label="제목" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.productName" maxlength="16" placeholder="제목을 입력하세요" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="categories" label="상품 분류" :label-width="state.formLabelWidth">
        <el-select v-model="state.form.categories" placeholder="카테고리를 선택해주세요">
          <el-option label="전자기기" value="electronics"></el-option>
          <el-option label="의류" value="clothing"></el-option>
        </el-select>
      </el-form-item>
      <!-- 가격 -->
      <!-- 숫자만 입력가능하다. -->
      <el-form-item prop="basePrice" label="가격" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.basePrice" placeholder="가격을 입력하세요 (단위: 원)" maxlength="10" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="off"></el-input>
      </el-form-item>
      <!-- 제품 판매 예약시간 -->
      <el-form-item prop="reserveTime" label="제품 판매 예약시간" :label-width="state.formLabelWidth">
        <el-col :span="30">
          <el-date-picker
            class="date_picker"
            v-model="state.form.reserveTime"
            type="datetime"
            placeholder="날자를 선택하세요"
            style="width: 100%;"
            :disabled-date="disabledDate"
            >
          </el-date-picker>
        </el-col>
      </el-form-item>
      <!-- 제품 설명 -->
      <el-form-item prop="description" label="제품 설명" :label-width="state.formLabelWidth">
        <el-input type="textarea" resize="none" :rows="5" v-model.trim="state.form.description" placeholder="내용을 입력하세요" show-word-limit maxlength="300" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
  </el-container>

  <!-- 작성, 취소버튼 -->
  <hr>
  <el-form-item>
    <el-button type="primary" @click="clickUpdate">작성</el-button>
    <el-button type="danger" @click="clickCancel">취소</el-button>
  </el-form-item>
</template>

<script>
import { onMounted, ref, reactive, onBeforeMount, } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'UpdateDealform',
  // setup 안에서 props를 사용하려면 여기서 정의를 해주어야 한다.
  props: {
    productId: [ String||Number ]
  },
  // props를 통해 productId값이 담겨온다.
  setup (props) {
    console.log(props.productId)
    const store = useStore()
    const router = useRouter()
    // 독립적인 반응형 값 생성 ref()
    const updateDealForm = ref(null)

    const state = reactive({
      form: {
        productName: '',
        categories: '',
        basePrice: '',
        reserveTime: '',
        description: '',
      },
      src: {
        imageUrl: '',
      },
      formValicate: false,
      loading: false,
      formLabelWidth: '140px',
    })
    //  페이지 진입 전 불리는 훅
    onBeforeMount(()=>{
      console.log('before mount')
      // axios처리로 data불러오기
      axios({
        url: `/product/${props.productId}`
      })
      .then(res=>{
        console.log(res)
        state.form.productName = res.data.productName
        state.form.categories = res.data.categories
        state.form.basePrice = res.data.basePrice
        state.form.reserveTime = res.data.reserveTime
        state.form.description = res.data.description
        state.src.imageUrl = res.data.imageUrl
      })
      // 게시글 정보 받아와서 폼의 prop로 보여주기
      // dispatch method로 requestProductInfo action 호출
      // props.productID가 payload에 담긴다.
      // store.dispatch('root/requestProductInfo', props.productId)
      //   .then(data=> {
      //     console.log(data)
      //   })
    })
    // 페이지 진입시 불리는 훅
    onMounted (() => {
      // mutations의 setMenuActiveMenuName을 호출하고 setMenuActiveMenuName의 create-deal-form 인자를 받아온다.
      store.commit('root/setMenuActiveMenuName', 'create-deal-form')
      //
    })

    const clickUpdate = function () {
      // console.log(state.form.categories)
      // console.log(state.form.reserveTime)
      state.loading = true
      // 작성 클릭 시 validate 체크 후 그 결과 값에 따라, 게시글 작성 API 호출 또는 경고창 표시
      // updateDealForm.value.validate((valid) => {
      //   if (valid) {
          console.log('submit')
          store.dispatch('root/updatePost', {
            productId: props.productId,
            imageUrl: state.src.imageUrl,
            productName: state.form.productName,
            categories: state.form.categories,
            basePrice: state.form.basePrice,
            reserveTime: state.form.reserveTime,
            description: state.form.description,
           })
            .then(res=>{
              //console.log(res.data)
              router.push({name:'home'})
            }).then(()=>{
              state.loading = false
            })
            .catch(err=>{
              state.loading = false
              console.log(err)
              alert("양식이 올바르지 않습니다!")
            })
      //   } else {
      //     state.loading = false
      //     alert('Validate error!')
      //   }
      // });
    }

    const clickCancel = function () {
      console.log('go home')
      window.location='/'
    }

    return { updateDealForm, state, clickUpdate, clickCancel, }
  },
  // imageUrl, el-date-picker 관련 method
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('Avatar picture must be JPG format!');
      }
      if (!isLt2M) {
        this.$message.error('사진 크기는 2MB를 초과할 수 없습니다!');
      }
      return isJPG && isLt2M;
    },
    disabledDate(time) {
      return time && time.valueOf() < Date.now();
      // return time.getTime() < Date.now() - 8.64e7
    },
  },

}
</script>
<style>
  .update-deal-form {
    /* justify-content: center; */
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
    font-size: 40px;
    color: #8c939d;
    width: 300px;
    height: 300px;
    line-height: 50%;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .el-picker-panel__footer .el-button--text.el-picker-panel__link-btn {
    display: none;
  }
</style>
