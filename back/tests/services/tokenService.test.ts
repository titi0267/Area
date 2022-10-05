import { describe, test, expect } from "@jest/globals";
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
      console.log(token);
      expect(token.discordToken).toBe("Discord");
      expect(token.githubToken).toBe(null);
      await UserService.removeUserById(users[0].id);
    });
  });
});
