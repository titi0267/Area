import { describe, test, expect } from "@jest/globals";
import ClientError from "../../src/error";

import { UserService } from "../../src/services";

describe("Test post user service", () => {
  describe("Test working cases", () => {
    test("Create one valid user", async () => {
      const token = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();

      expect(token.token.length).toBeGreaterThan(1);
      expect(users[0].firstName).toBe("Ludo");

      await UserService.removeUserById(users[0].id);
    });
  });

  describe("Test error cases", () => {
    test("Create user but email already exist", async () => {
      await UserService.createUser("Ludo", "Str", "ludostr@mail.com", "passwd");

      try {
        await UserService.createUser(
          "Budo",
          "Str",
          "ludostr@mail.com",
          "passwd",
        );
      } catch (e) {
        expect(e.status).toBe(400);
        await UserService.removeUserByEmail("ludostr@mail.com");
      }
    });
  });
});

describe("Test remove user service by email", () => {
  describe("Test working cases", () => {
    test("Remove a single user", async () => {
      await UserService.createUser("Sudo", "Str", "ludostr@mail.com", "passwd");

      const beforeUsers = await UserService.getAllUsers();
      const removedUser = await UserService.removeUserByEmail(
        "ludostr@mail.com",
      );
      const afterUser = await UserService.getAllUsers();

      expect(beforeUsers).toHaveLength(1);
      expect(removedUser.firstName).toBe("Sudo");
      expect(afterUser).toHaveLength(0);
    });
  });

  describe("Test error cases", () => {
    test("Remove user that does not exist", async () => {
      expect(UserService.removeUserByEmail("paul@paul.fr")).rejects.toThrow(
        ClientError,
      );
    });
  });
});

describe("Test remove user service by Id", () => {
  describe("Test working cases", () => {
    test("Remove a single user with number id", async () => {
      await UserService.createUser("Sudo", "Str", "ludostr@mail.com", "passwd");

      const beforeUsers = await UserService.getAllUsers();
      const removedUser = await UserService.removeUserById(beforeUsers[0].id);
      const afterUser = await UserService.getAllUsers();

      expect(beforeUsers).toHaveLength(1);
      expect(removedUser.firstName).toBe("Sudo");
      expect(afterUser).toHaveLength(0);
    });

    test("Remove a single user with string id", async () => {
      await UserService.createUser("Sudo", "Str", "ludostr@mail.com", "passwd");

      const beforeUsers = await UserService.getAllUsers();
      const removedUser = await UserService.removeUserById(
        String(beforeUsers[0].id),
      );
      const afterUser = await UserService.getAllUsers();

      expect(beforeUsers).toHaveLength(1);
      expect(removedUser.firstName).toBe("Sudo");
      expect(afterUser).toHaveLength(0);
    });
  });

  describe("Test error cases", () => {
    test("Remove user that does not exist", async () => {
      expect(UserService.removeUserById(12)).rejects.toThrow(ClientError);
    });
  });
});

describe("Test list all users", () => {
  describe("Test working cases", () => {
    test("Test list all users with two", async () => {
      await UserService.createUser("Sudo", "Str", "ludostr@mail.com", "passwd");
      await UserService.createUser("Qudo", "Str", "ludospr@mail.com", "passwd");

      const userList = await UserService.getAllUsers();

      expect(userList).toHaveLength(2);
      expect(userList[0].firstName).toBe("Sudo");
      expect(userList[1].firstName).toBe("Qudo");

      await UserService.removeUserByEmail("ludostr@mail.com");
      await UserService.removeUserByEmail("ludospr@mail.com");
    });

    test("Test list all users with no users", async () => {
      const userList = await UserService.getAllUsers();

      expect(userList).toHaveLength(0);
    });
  });
});
