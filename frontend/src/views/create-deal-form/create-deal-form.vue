<template>
  <el-container class="create-deal-form">
    <!-- 이미지 업로드 폼 -->
    <form class="image-upload-form" method="post" enctype="multipart/form-data">
      <img class="wrapper" :src="state.src.imageUrl" alt="">
      <div class="button">
          <label for="chooseFile">
              👉 이곳을 눌러 사진을 업로드 하세요 👈
              <br>
              (다시 눌러 사진을 변경할 수 있습니다.)
          </label>
      </div>
      <input type="file" id="chooseFile" name="chooseFile" accept="image/*" :onchange="loadFile">
    </form>

    <!-- 거래 작성 폼 -->
    <el-form v-if="!state.loading" :model="state.form" :rules="state.rules" ref="createDealForm" :label-position="state.form.align">
      <!-- 게시글 제목 -->
      <el-form-item prop="productName" label="제목" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.productName" placeholder="제목을 입력하세요" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="categories" label="상품 분류" :label-width="state.formLabelWidth">
        <el-select v-model="state.form.categories" placeholder="카테고리를 선택해주세요">
          <el-option label="선택하세요" value=""></el-option>
          <el-option label="디지털/가전" value="digital"></el-option>
          <el-option label="가구인테리어" value="furniture"></el-option>
          <el-option label="패션잡화" value="fashion"></el-option>
          <el-option label="미술품" value="art"></el-option>
        </el-select>
      </el-form-item>
      <!-- 가격 -->
      <!-- 숫자만 입력가능하다. -->
      <el-form-item prop="basePrice" label="가격" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.basePrice" placeholder="가격을 입력하세요 (단위: 원)" maxlength="12" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="off"></el-input>
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
        <el-input type="textarea" resize="none" :rows="5" v-model="state.form.description" placeholder="내용을 입력하세요" show-word-limit maxlength="300" autocomplete="off"></el-input>
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
        productName: '',
        categories: '',
        basePrice: '',
        reserveTime: '',
        description: '',
        registTime: new Date(),
        restrictTime: '',
        state: '',
      },
      src: {
        imageUrl:'',
      },
      rules: {
        productName:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
          {max:16, message: '최대 16자까지 입력 가능합니다.'}
        ],
        categories:[
          {required: true, message: '필수 선택 항목입니다.', trigger: 'blur'},
        ],
        basePrice:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
        ],
        reserveTime:[
          {required: true, message: '필수 선택 항목입니다.', trigger: 'blur'},
        ],
        description:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
        ],
      },
      formValicate: false,
      loading: false,
      formLabelWidth: '140px',
      date:'',
      date1:'',
    })
    // 페이지 진입시 불리는 훅
    onMounted (() => {
      // mutations의 setMenuActiveMenuName을 호출하고 setMenuActiveMenuName의 create-deal-form 인자를 받아온다.
      store.commit('root/setMenuActiveMenuName', 'create-deal-form')
    })

    // 이미지 파일 처리
    const loadFile = function (res) {
      const imgUrl = URL.createObjectURL(res.path[0].files[0])
      // console.log(imgUrl)
      state.src.imageUrl = imgUrl
    }

    // reserveTime 의 타입은 String이다.
    const dateTimeToString = function () {
      // 단어별로 구분
      // reserveTime
      const array = state.form.reserveTime.toString().split(' ')
      // registTime
      const array1 = state.form.registTime.toString().split(' ')
      // 그 단어의 1,2,3,4번째 배열만 쓸거다
      const month = ["","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      let res = `${array[3]}-`
      for(let i = 1; i<=12; i++){
        if(array[1]==month[i]){
          if(i<10)
            res+=`0${i}-`
          else
            res += `${i}-`
          break;
        }
      }
      let res1 = `${array1[3]}-`
      for(let i = 1; i<=12; i++){
        if(array1[1]==month[i]){
          if(i<10)
            res1+=`0${i}-`
          else
            res1 += `${i}-`
          break;
        }
      }
      res += `${array[2]}T${array[4]}.000+00:00`
      res1 += `${array1[2]}T${array1[4]}.000+00:00`
      state.date = res
      state.date1 = res1
    }

    const saveFile = function (pid) {
      // console.log("saveFile func")
      let img = document.getElementById('chooseFile')

      let fd = new FormData()
      fd.append('imageFile', img.files[0])

      const req = {
        imageFile: fd,
        productId: pid
      }
      store.dispatch('root/requestUploadImage', req)
      .then(res => {
        //console.log(res)
        alert('상품 등록이 완료되었습니다!')
        // router.push({name: 'home'})
        window.location = '/'
      })
      .catch(err=>{
        console.log(err)
      })
    }

    const clickCreate = function () {
      if(state.src.imageUrl==''){
        // console.log('이미지 없음')
        alert('이미지는 필수 입니다')
        return
      }
      // console.log(state.form.basePrice)
      // console.log(state.date1)
      // backend에서 원하는 형식으로 주기 위해 현재시간과 예약시간의 형태를 변경하는 함수
      dateTimeToString()
      //console.log(state.date)
      //console.log(typeof state.date)
      state.loading = true
      createDealForm.value.validate((valid) => {
        if (valid) {
          const body ={
            imageUrl: state.src.imageUrl,
            productName: state.form.productName,
            categories: state.form.categories,
            basePrice: parseInt(state.form.basePrice),
            registTime: state.date1,
            reserveTime: state.date,
            description: state.form.description,
              // restrictTime: state.form.restrictTime,
              // state: state.form.state,
          }


          // 작성 클릭 시 validate 체크 후 그 결과 값에 따라, 게시글 작성 API 호출 또는 경고창 표시
          store.dispatch('root/createPost', body)
          .then(res=>{
            // console.log(res.data)
            let pid = res.data.productId
            // console.log(pid)
            saveFile(pid)
            //router.push({name: 'home'})
          })
          .catch(err=>{
            state.loading = false
            alert('게시글 작성에 실패하였습니다.')
            console.log(err)
          })
        } else if(!valid){
          state.loading = false
          alert('필수 항목을 입력하세요.')
        }
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
      // console.log('go home')
      window.location='/'
    }

    return { createDealForm, state, clickCreate, clickCancel, loadFile, saveFile }
  },
  // imageUrl, el-date-picker 관련 method
  methods: {
    disabledDate(time) {
      return time && time.valueOf() < Date.now()- 8.64e7;
      // return time.getTime() < Date.now() - 8.64e7
    },
  },
}
</script>

<style>
  .create-deal-form {
    justify-content: center;
  }
  /* 이미지 업로드 폼 */
    .image-upload-form {
      max-width: 500px;
      max-height: 500px;
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
  /* 이미지 태그 비율 맞추기 */
  .wrapper {

    width: 400px;
    height: 400px;
  }
  img {
    width: 100%;
    height: 100%;
    /* 업로드 이미지 가로세로 비율 맞춰주기 */
    object-fit: cover;
  }

</style>
