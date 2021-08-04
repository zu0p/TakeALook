<template>
  <!-- 사진 업로드 -->
  <el-container class="create-deal-form">
    <!-- 이미지 -->
    <form method="post" enctype="multipart/form-data">
      <div class="button">
          <label for="chooseFile">
              👉 CLICK HERE! 👈
          </label>
      </div>
      <input type="file" id="chooseFile" name="chooseFile" accept="image/*" :onchange="loadFile">
    </form>

    <!-- 거래 작성 폼 -->
    <el-form v-if="!state.loading" v-model="state.form" ref="createDealForm" :label-position="state.form.align">
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
    <el-button type="primary" @click="clickCreate">작성</el-button>
    <el-button type="danger" @click="clickCancel">취소</el-button>
  </el-form-item>
</template>

<script>
import { onMounted, ref, reactive, } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'


export default {
  name: 'CreateDealForm',

  setup () {
    const store = useStore()
    // 독립적인 반응형 값 생성 ref()
    const createDealForm = ref(null)

    const router = useRouter()
    const state = reactive({
      form: {
        basePrice:0,
        categories:'',
        description:'',
        productName:'',
        registTime:'',
        reserveTime:'',
        restrictTime:'',
        state:'',
      },
      src: {
        imageUrl:'',
      },
      formValicate: false,
      loading: false,
      formLabelWidth: '140px',
      date:'',
    })
    // 페이지 진입시 불리는 훅
    onMounted (() => {
      // mutations의 setMenuActiveMenuName을 호출하고 setMenuActiveMenuName의 create-deal-form 인자를 받아온다.
      store.commit('root/setMenuActiveMenuName', 'create-deal-form')
    })

    // 이미지 파일 처리
    // const loadFile = function (res) {
    //   const imgUrl = URL.createObjectURL(res.path[0].files[0])
    //   state.src.imageUrl = imgUrl
    // }
    const loadFile = function (e) {
      console.log(e.target.files[0])
      // let img = e.target.files[0]

      // let fd = new FormData()
      // fd.append('image', img)
      // console.log(fd)

    }

    const showImage = function () {
      //이미지는 화면에 나타나고
      newImage.style.visibility = "visible";

      //이미지 업로드 버튼은 숨겨진다
      document.getElementById('image-upload').style.visibility = 'hidden';

      document.getElementById('fileName').textContent = null;     //기존 파일 이름 지우기
    }

    // reserveTime1 의 타입은 String이다.
    const dateTimeToString = function () {
      // 단어별로 구분
      const array = state.form.reserveTime.toString().split(' ')
      // 그 단어의 1,2,3,4번째 배열만 쓸거다
      const month = ["","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      let res = `${array[3]}-`
      for(let i = 1; i<=12; i++){
        if(array[1]==month[i]){
          if(i<10)
            res+=`0${i}-`
          else
            res += `i-`
          break;
        }
      }
      res += `${array[2]}T${array[4]}.000+00:00`
      state.date = res
    }


    const clickCreate = function () {
      //
      dateTimeToString()
      //console.log(state.date)
      //console.log(typeof state.date)
      state.loading = true

      const body ={
          basePrice: parseInt(state.form.basePrice),
          categories: state.form.categories,
          description: state.form.description,
          imageUrl: state.src.imageUrl,
          productName: state.form.productName,
          // registTime: state.form.registTime,
          reserveTime: state.date,
          // restrictTime: state.form.restrictTime,
          // state: state.form.state,
      }
      console.log(body)
      // 작성 클릭 시 validate 체크 후 그 결과 값에 따라, 게시글 작성 API 호출 또는 경고창 표시
      store.dispatch('root/createPost', body)
      .then(res=>{
        console.log(res)
        router.push({name: 'home'})
      })
      // createDealForm.value.validate((valid) => {
      //   if (valid) {
      //     console.log('submit')
      //     store.dispatch('root/createPost', {
      //       imageUrl: state.src.imageUrl,
      //       productName: state.form.productName,
      //       categories: state.form.categories,
      //       basePrice: state.form.basePrice,
      //       reserveTime: state.form.reserveTime,
      //       description: state.form.description,
      //      })
      //       .then(res=>{
      //         console.log(res.data)
      //         window.location='/'
      //       }).then(()=>{
      //         state.loading = false
      //       })
      //       .catch(err=>{
      //         state.loading = false
      //         console.log(err)
      //         alert("양식이 올바르지 않습니다!")
      //       })
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

    return { createDealForm, state, clickCreate, clickCancel, loadFile, showImage }
  },
  // imageUrl, el-date-picker 관련 method
  methods: {
    disabledDate(time) {
      return time && time.valueOf() < Date.now();
      // return time.getTime() < Date.now() - 8.64e7
    },
  },

}
</script>
<style>
  .create-deal-form {
    /* justify-content: center; */
  }
  /* label 스타일 조정 */
  .button {
    display: flex;
    justify-content: center;
  }
  label {
    cursor: pointer;
    font-size: 1em;
  }

  /* 못생긴 기존 input 숨기기 */
  #chooseFile {
    visibility: hidden;
  }
</style>
