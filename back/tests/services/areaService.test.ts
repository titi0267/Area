import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";
import ClientError from "../../src/error";

import { AreaService, UserService } from "../../src/services/";

describe("Test post area service", () => {
  describe("Test working cases", () => {
    test("Create one valid area", async () => {
      await UserService.createUser("Ludo", "Str", "tim@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await AreaService.createArea(
        "Youtube",
        "like",
        "test",
        "Spotify",
        "Add to playlist",
        "test",
        users[0].id,
      );
      const areas = await AreaService.getAllArea();

      expect(areas[0].actionService).toBe("Youtube");

      await AreaService.removeAreaById(areas[0].id);
      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid user id", () => {
    test("Create one invalid area", async () => {
      try {
        await AreaService.createArea(
          "Youtube",
          "like",
          "test",
          "Spotify",
          "Add to playlist",
          "test",
          1,
        );
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test remove area by id", () => {
  describe("Test working cases", () => {
    test("Remove one area", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await AreaService.createArea(
        "Youtube",
        "like",
        "test",
        "Spotify",
        "Add to playlist",
        "test",
        users[0].id,
      );
      const beforeAreas = await AreaService.getAllArea();

      const removedArea = await AreaService.removeAreaById(beforeAreas[0].id);
      const afterAreas = await AreaService.getAllArea();
      await UserService.removeUserById(users[0].id);

      expect(removedArea.action).toHaveLength(4);
      expect(beforeAreas[0].action).toBe("like");
      expect(afterAreas).toHaveLength(0);
    });
  });
  describe("Test invalid cases", () => {
    test("Remove invalid area with number", async () => {
      try {
        await AreaService.removeAreaById(1);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
    test("Remove invalid area with string", async () => {
      try {
        await AreaService.removeAreaById("1");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});
