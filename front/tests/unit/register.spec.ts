import { describe, test, expect, it } from "@jest/globals";

/*describe("Render Register", () => {
  it("Render without error", () => {
    const wrapper = shallowMount(Register, {
      components: {
        Register,
      },
      data() {
        return {
          register: {
            password: {
              value: "passwordD1?",
              error: "",
              valide: false,
            },
            confirmPassword: {
              value: "passwordD2?",
              error: "",
              valide: false,
            },
            timeout: false,
            validate: false,
          },
        };
      },
    });
  });
});*/

/*    wrapper.vm.$nextTick(() => {
      console.log(wrapper.vm.$data.register.confirmPassword.error);
    });
  });*/
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

describe("Register.vue", () => {
  it("Check not the same password", () => {
    console.log("test try");
  });
}); //Register.checkPassword(Register.password, "password");
//Register.checkPassword(Register.confirmPassword, "confirmPassword");
//expect(Register.register["confirmPassword"].error).toBe(
//      "Password are not the same"
//  );
//console.log(wrapper.toContain());
// .toContain('Password are not the same')
// console.log(samePassword)
