// API
import $axios from 'axios'

export function requestLogin ({ state, commit }, payload) {
  //console.log('requestLogin', state, payload)
  const url = 'http://localhost:8080/api/v1/auth/login'
  let body = payload

  return $axios.post(url, body)
}

export function requestSignup({state}, payload){
  const url = 'http://localhost:8080/api/v1/users'
  let body = payload

  return $axios.post(url, body)
}

export function requestCheckDupl({state}, payload){
  //console.log(payload)
  const url = `http://localhost:8080/api/v1/users/${payload.id}`
  return $axios.get(url)
}

export function requestUserInfo({commit}, payload){
  const url = '/api/v1/users/me'
  $axios.get(url)
    .then(res=>{
      commit("SET_USER_INFO", res.data.userId)
    })
    .catch((err)=>{
      console.log(err)
    })
}
