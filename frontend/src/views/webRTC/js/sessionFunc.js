import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import ws from './webSocket'

const store = useStore()
const router = useRouter()

export function buyerJoinFunc(name, pid, price){
  const req = {
    userId: name,
    productId: pid
  }
  // console.log(req)

  // 서버에 요청보내서 res의 room != none이면 입장
  this.$store.dispatch('root/requestJoinTrade', req)
    .then(res=>{
      let room = res.data.room
      // console.log(isActive)
      if(room=='none'){
        alert("거래 세션이 생성되지 않아 입장할 수 없습니다.")
      }
      else if(room=='alreadyStarted'){
        alert("거래 가격제안이 시작되어 입장할 수 없습니다.")
      }
      else{
        // 거래 세션에 입장
        router.push({
          name: 'meeting-detail',
          params: {
            meetingId: room,
            userId: state.name,
            isSeller: 0,
            basePrice: price
          },
        })
      }
    })
}
