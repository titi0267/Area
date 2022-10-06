import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";
import { UserService, TokenService } from "../../src/services";

describe("Test put token service", () => {
  describe("Test working cases", () => {
    test("Update one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      const token = await TokenService.updateToken(
        "Discord",
        undefined,
        undefined,
        "Youtube",
        undefined,
        undefined,
        users[0].id,
      );
      expect(token.discordToken).toBe("Discord");
      expect(token.githubToken).toBe(null);
      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Put token with invalid userId", async () => {
      try {
        await TokenService.updateToken(
          "Discord",
          undefined,
          undefined,
          "Youtube",
          undefined,
          undefined,
          1,
        );
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});
