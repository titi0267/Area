import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";

import { AreaService, UserService } from "../../src/services/";

describe("Test get all area service", () => {
  describe("Test working cases", () => {
    test("Get all area", async () => {
      await UserService.createUser("Ludo", "Str", "tim@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await AreaService.createArea(1, 1, "test", 2, 1, "test", users[0].id);
      const areas = await AreaService.getAllArea();

      expect(areas[0].actionServiceId).toBe(1);
      expect(areas[0].reactionId).toBe(1);

      await AreaService.removeAreaById(areas[0].id);
      await UserService.removeUserById(users[0].id);
    });
  });
});

describe("Test post area service", () => {
  describe("Test working cases", () => {
    test("Create one valid area", async () => {
      await UserService.createUser("Ludo", "Str", "tim@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await AreaService.createArea(1, 1, "test", 2, 1, "test", users[0].id);
      const areas = await AreaService.getAllArea();

      expect(areas[0].actionServiceId).toBe(1);

      await AreaService.removeAreaById(areas[0].id);
      await UserService.removeUserById(users[0].id);
    });
  });
  describe("Test invalid user id", () => {
    test("Create one invalid area", async () => {
      try {
        await AreaService.createArea(1, 1, "test", 45, 1, "test", 1);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
    test("Create one area with invalid user id", async () => {
      try {
        await AreaService.createArea(1, 1, "test", 2, 1, "test", -1);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test get area by user id", () => {
  describe("Test working cases", () => {
    test("Get a area from a user", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await AreaService.createArea(1, 1, "test", 2, 1, "test", users[0].id);
      const areas = await AreaService.getAreasByUserId(users[0].id);

      expect(areas[0].actionServiceId).toBe(1);
      expect(areas[0].reactionServiceId).toBe(2);

      await AreaService.removeAreaById(areas[0].id);
      await UserService.removeUserById(users[0].id);
    });
  });

  describe("Test invalid cases", () => {
    test("Get areas with invalid user id", async () => {
      try {
        await AreaService.getAreasByUserId(1);
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

      await AreaService.createArea(1, 1, "test", 2, 1, "test", users[0].id);
      const beforeAreas = await AreaService.getAllArea();

      const removedArea = await AreaService.removeAreaById(beforeAreas[0].id);
      const afterAreas = await AreaService.getAllArea();
      await UserService.removeUserById(users[0].id);

      expect(removedArea.actionId).toBe(1);
      expect(beforeAreas[0].actionId).toBe(1);
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

describe("Test update area values", () => {
  describe("Test working cases", () => {
    test("Update one area", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      const areaBefore = await AreaService.createArea(
        1,
        1,
        "test",
        2,
        1,
        "test",
        users[0].id,
      );
      const areaAfter = await AreaService.updateAreaValues(
        areaBefore.id,
        "Value",
      );

      expect(areaBefore.lastActionValue).toBeNull();
      expect(areaAfter.lastActionValue).toBe("Value");

      await AreaService.removeAreaById(areaBefore.id);
      await UserService.removeUserById(users[0].id);
    });
  });

  describe("Test invalid cases", () => {
    test("Update area with invalid Id", async () => {
      try {
        await AreaService.updateAreaValues(45, "Value");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test edit area", () => {
  describe("Test working cases", () => {
    test("Edit one area", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      const areaBefore = await AreaService.createArea(
        1,
        1,
        "test",
        2,
        1,
        "test",
        users[0].id,
      );

      const areaAfter = await AreaService.editArea(
        users[0].id,
        areaBefore.id,
        false,
        "lol",
        "lol",
      );

      expect(areaBefore.enabled).toBeTruthy();
      expect(areaAfter.enabled).toBeFalsy();
      expect(areaBefore.actionParam).toBe("test");
      expect(areaAfter.actionParam).toBe("lol");
      expect(areaBefore.reactionParam).toBe("test");
      expect(areaAfter.reactionParam).toBe("lol");

      await AreaService.removeAreaById(areaBefore.id);
      await UserService.removeUserById(users[0].id);
    });

    test("Edit one area with empty fields", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      const areaBefore = await AreaService.createArea(
        1,
        1,
        "test1",
        2,
        1,
        "test2",
        users[0].id,
      );

      const areaAfter = await AreaService.editArea(
        users[0].id,
        areaBefore.id,
        null,
        null,
        null,
      );

      expect(areaBefore.enabled).toBeTruthy();
      expect(areaAfter.enabled).toBeTruthy();
      expect(areaBefore.actionParam).toBe("test1");
      expect(areaAfter.actionParam).toBe("test1");
      expect(areaBefore.reactionParam).toBe("test2");
      expect(areaAfter.reactionParam).toBe("test2");

      await AreaService.removeAreaById(areaBefore.id);
      await UserService.removeUserById(users[0].id);
    });
  });

  describe("Test invalid cases", () => {
    test("Edit area with invalid userId and areaId", async () => {
      try {
        await AreaService.editArea(45, 45, null, null, null);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });

    test("Edit area with valid ids but area didn't belong to user", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      await UserService.createUser("Ludo", "Str", "test@gmail.com", "passwd");
      const users = await UserService.getAllUsers();

      const area = await AreaService.createArea(
        1,
        1,
        "test",
        2,
        1,
        "test",
        users[0].id,
      );

      try {
        await AreaService.editArea(users[1].id, area.id, false, null, null);
      } catch (e) {
        expect(e.status).toBe(httpStatus.UNAUTHORIZED);
        await AreaService.removeAreaById(area.id);
        await UserService.removeUserById(users[0].id);
        await UserService.removeUserById(users[1].id);
      }
    });
  });
});
