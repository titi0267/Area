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
  });
});

describe("Test set Spotify token", () => {
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
