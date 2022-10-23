import { shallowMount } from '@vue/test-utils'
import Register from '@/views/Register.vue'

describe('Register.vue', () => {
  it('renders props.msg when passed', () => {
    const samePassword = true
    const wrapper = shallowMount(Register, {
      propsData: { samePassword }
    })
    // console.log(samePassword)
  })
})
