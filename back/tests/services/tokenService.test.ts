import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";
import { UserService, TokenService } from "../../src/services";

describe("Test set Spotify token", () => {
  describe("Test working cases", () => {
    test("Set one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      const token = await TokenService.setSpotifyToken(users[0].id, "token");

      expect(token.spotifyToken).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Put token with invalid userId", async () => {
      try {
        await TokenService.setSpotifyToken(56, "token");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });

    test("Put undefined token", async () => {
      try {
        await TokenService.setSpotifyToken(1, undefined);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test get Spotify token", () => {
  describe("Test working cases", () => {
    test("Get one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      await TokenService.setSpotifyToken(users[0].id, "token");
      const token = await TokenService.getSpotifyToken(users[0].id);

      expect(token).not.toBe(null);
      expect(token).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Get token with invalid userId", async () => {
      const token = await TokenService.getSpotifyToken(56);

      expect(token).toBe(null);
    });
  });
});

describe("Test set Github token", () => {
  describe("Test working cases", () => {
    test("Get one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      await TokenService.setGithubToken(users[0].id, "token");
      const token = await TokenService.getGithubToken(users[0].id);

      expect(token).not.toBe(null);
      expect(token).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Get token with invalid userId", async () => {
      const token = await TokenService.getGithubToken(56);

      expect(token).toBe(null);
    });
  });
});

describe("Test get Github token", () => {
  describe("Test working cases", () => {
    test("Set one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      const token = await TokenService.setGithubToken(users[0].id, "token");

      expect(token.githubToken).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Put token with invalid userId", async () => {
      try {
        await TokenService.setGithubToken(56, "token");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });

    test("Put undefined token", async () => {
      try {
        await TokenService.setGithubToken(1, undefined);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test set Google token", () => {
  describe("Test working cases", () => {
    test("Set one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      const token = await TokenService.setGoogleToken(users[0].id, "token");

      expect(token.googleToken).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Put token with invalid userId", async () => {
      try {
        await TokenService.setGoogleToken(56, "token");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });

    test("Put undefined token", async () => {
      try {
        await TokenService.setGoogleToken(1, undefined);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test set Google token", () => {
  describe("Test working cases", () => {
    test("Get one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      await TokenService.setGoogleToken(users[0].id, "token");
      const token = await TokenService.getGoogleToken(users[0].id);

      expect(token).not.toBe(null);
      expect(token).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Get token with invalid userId", async () => {
      const token = await TokenService.getGoogleToken(56);

      expect(token).toBe(null);
    });
  });
});

describe("Test set Discord infos", () => {
  describe("Test working cases", () => {
    test("Set one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      const token = await TokenService.setDiscordInfos(
        users[0].id,
        "token",
        "guild_id",
      );

      expect(token.discordToken).toBe("token");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid userId", () => {
    test("Put token with invalid userId", async () => {
      try {
        await TokenService.setDiscordInfos(56, "token", "guild_id");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test get Discord infos", () => {
  describe("Test working cases", () => {
    test("Get one token field", async () => {
      const user = await UserService.createUser(
        "Ludo",
        "Str",
        "ludostr@mail.com",
        "passwd",
      );
      const users = await UserService.getAllUsers();
      await TokenService.setDiscordInfos(users[0].id, "token", "guild_id");
      const infos = await TokenService.getDiscordInfos(users[0].id);

      expect(infos).not.toBe(null);
      expect(infos?.discordToken).toBe("token");
      expect(infos?.guildId).toBe("guild_id");

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid cases", () => {
    test("Get token with invalid userId", async () => {
      const infos = await TokenService.getDiscordInfos(56);

      expect(infos).toBe(null);
    });
  });
});

describe("Test remove user token", () => {
  describe("Test working cases", () => {
    test("Remove one token field", async () => {
      await UserService.createUser("Ludo", "Str", "ludostr@mail.com", "passwd");
      const users = await UserService.getAllUsers();
      await TokenService.setDiscordInfos(users[0].id, "token", "guild_id");
      const infos = await TokenService.getDiscordInfos(users[0].id);
      const tokenTableAfter = await TokenService.removeUserToken(
        users[0].id,
        true,
        true,
        false,
        false,
        false,
      );

      expect(infos).not.toBe(null);
      expect(infos?.discordToken).toBe("token");
      expect(infos?.guildId).toBe("guild_id");
      expect(tokenTableAfter.discordToken).toBeNull();

      await UserService.removeUserById(users[0].id);
    });

    test("Remove all token field", async () => {
      await UserService.createUser("Ludo", "Str", "ludostr@mail.com", "passwd");
      const users = await UserService.getAllUsers();
      await TokenService.setDiscordInfos(users[0].id, "token", "guild_id");
      const discordInfos = await TokenService.getDiscordInfos(users[0].id);
      const tokenTableAfter = await TokenService.removeUserToken(
        users[0].id,
        true,
        true,
        true,
        true,
        true,
      );

      expect(discordInfos).not.toBe(null);
      expect(discordInfos?.discordToken).toBe("token");
      expect(discordInfos?.guildId).toBe("guild_id");
      expect(tokenTableAfter.discordToken).toBeNull();
      expect(tokenTableAfter.googleToken).toBeNull();

      await UserService.removeUserById(users[0].id);
    });

    test("Remove no token field", async () => {
      await UserService.createUser("Ludo", "Str", "ludostr@mail.com", "passwd");
      const users = await UserService.getAllUsers();
      await TokenService.setDiscordInfos(users[0].id, "token", "guild_id");
      const infos = await TokenService.getDiscordInfos(users[0].id);
      const tokenTableAfter = await TokenService.removeUserToken(
        users[0].id,
        undefined,
        undefined,
        undefined,
        undefined,
        undefined,
      );

      expect(infos).not.toBe(null);
      expect(infos?.discordToken).toBe("token");
      expect(infos?.guildId).toBe("guild_id");
      expect(tokenTableAfter.discordToken).not.toBeNull();

      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid cases", () => {
    test("Remove with invalid userId", async () => {
      try {
        await TokenService.removeUserToken(56, true, true, false, false, false);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});
