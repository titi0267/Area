import { describe, test, expect } from "@jest/globals";

import { RawRegisterBody } from "../../src/types/body/userRequestBody.types";
import { RawAreaBody } from "../../src/types/body/areaRequestBody.types";
import * as BodyHelper from "../../src/helpers/body.helpers";
import ClientError from "../../src/error";

describe("Test register body helper", () => {
  describe("Test working cases", () => {
    test("Get a valid register body", async () => {
      const rawRegisterBody: RawRegisterBody = {
        firstName: "ludo",
        lastName: "str",
        email: "ludo@email.com",
        password: "password",
      };

      const formatedBody = BodyHelper.checkRegisterBody(rawRegisterBody);

      expect(formatedBody.firstName).toBe("ludo");
    });
  });

  describe("Test bad cases", () => {
    test("Get a register body but one field undefined", async () => {
      const rawRegisterBody: RawRegisterBody = {
        firstName: "ludo",
        lastName: "str",
        email: "ludo@email.com",
        password: undefined,
      };

      try {
        BodyHelper.checkRegisterBody(rawRegisterBody);
      } catch (e) {
        expect(e.status).toBe(400);
      }
    });
  });
});

describe("Test area body helper", () => {
  describe("Test working cases", () => {
    test("Get a valid area body", async () => {
      const rawAreaBody: RawAreaBody = {
        actionService: "Youtube",
        action: "like",
        actionParam: "test",
        reactionService: "Spotify",
        reaction: "Add to playlist",
        reactionParam: "test",
        userId: 1,
      };

      const formatedBody = BodyHelper.checkAreaBody(rawAreaBody);

      expect(formatedBody.actionService).toBe("Youtube");
    });
  });

  describe("Test bad cases", () => {
    test("Get a Area body but one field undefined", async () => {
      const rawAreaBody: RawAreaBody = {
        actionService: "on",
        action: "zu",
        actionParam: "in",
        reactionService: undefined,
        reaction: "to",
        reactionParam: "vu",
        userId: 1,
      };

      try {
        BodyHelper.checkAreaBody(rawAreaBody);
      } catch (e) {
        expect(e.status).toBe(400);
      }
    });
  });
});
