import { shallowMount, mount } from '@vue/test-utils';
import { describe, test, expect, it } from "@jest/globals";
import Register from '@/views/Register.vue';
import { cp } from 'fs';

// describe('Register.vue', () => {
//   it('Error if a field is empty', () => {
//     const wrapper = shallowMount(Register);
//     wrapper.setData({
//       register: {
//         firstName: ''
//       }
//     })
//     expect(wrapper.text()).toContain("Maxime")
//     // console.log(samePassword)
//   })
// })

describe('Register.vue', () => {
  const wrapper = shallowMount(Register);
  wrapper.setData({
    samePassword: false,
    register: {
      password: 'password1',
      confirmPassword: 'password2'
    }
  })
  it('Check not the same password', () => {
    console.log((wrapper.find('.confirmPassword').find('.is-danger').exists()))
    // .toContain('Password are not the same')
    // console.log(samePassword)
  })
})
