// API
import axios from 'axios'
import { setInterceptors } from '../../../common/interceptors'

function createInstance(){
  const instance = axios.create()
  return setInterceptors(instance)
}
const BASE_URL = 'http://localhost:8080/api/v1'
const instanceWithAuth = createInstance()

export function requestLogin ({ state, commit }, payload) {
  //console.log('requestLogin', state, payload)
  const url = BASE_URL+'/auth/login'
  let body = payload

  //return axios.post(url, body)
  return instanceWithAuth.post(url, body)
}

export function requestSignup({state}, payload){
  const url = BASE_URL+'/users'
  let body = payload

  return instanceWithAuth.post(url, body)
}

export function requestCheckDupl({state}, payload){
  //console.log(payload)
  const url = BASE_URL+`/users/${payload.id}`
  return instanceWithAuth.get(url)
}

export function requestUserInfo({commit}, payload){
  const url = BASE_URL+'/users/me'
  instanceWithAuth.get(url)
    .then(res=>{
      commit("SET_USER_INFO", res.data.userId)
    })
    .catch((err)=>{
      console.log(err)
    })
}
