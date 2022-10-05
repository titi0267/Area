import { describe, test, expect } from "@jest/globals";

import { RawAreaBody } from "../../src/types/body/areaRequestBody.types";
import ClientError from "../../src/error";
import {
  RawRegisterBody,
  RawLoginBody,
} from "../../src/types/body/userRequestBody.types";
import * as BodyHelper from "../../src/helpers/body.helpers";
import httpStatus from "http-status";

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
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
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
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});
describe("Test login body helper", () => {
  describe("Test working cases", () => {
    test("Get a valid login body", async () => {
      const rawLoginBody: RawLoginBody = {
        email: "ludo@email.com",
        password: "password",
      };

      const formatedBody = BodyHelper.checkLoginBody(rawLoginBody);

      expect(formatedBody.email).toBe("ludo@email.com");
    });
  });

  describe("Test bad cases", () => {
    test("Get a login body but one field undefinied", async () => {
      const rawLoginBody: RawLoginBody = {
        email: "ludo@email.com",
        password: undefined,
      };

      try {
        BodyHelper.checkLoginBody(rawLoginBody);
      } catch (e) {
        expect(e.status).toBe(400);
      }
    });
  });
});
