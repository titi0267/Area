import { describe, test, expect } from "@jest/globals";

import { RawAreaBody } from "../../src/types/body/areaRequestBody.types";
import {
  RawRegisterBody,
  RawLoginBody,
} from "../../src/types/body/userRequestBody.types";
import * as BodyHelper from "../../src/helpers/body.helpers";
import httpStatus from "http-status";
import { RawTokenBody } from "../../src/types/body/tokenRequestBody.types";

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
        actionServiceId: "1",
        actionId: "1",
        actionParam: "test",
        reactionServiceId: "2",
        reactionId: "1",
        reactionParam: "test",
        userId: 1,
      };

      const formatedBody = BodyHelper.checkAreaBody(rawAreaBody);

      expect(formatedBody.actionServiceId).toBe(1);
    });
  });
  describe("Test bad cases", () => {
    test("Get a Area body but one field undefined", async () => {
      const rawAreaBody: RawAreaBody = {
        actionServiceId: "1",
        actionId: "1",
        actionParam: "in",
        reactionServiceId: "1",
        reactionId: "2",
        reactionParam: undefined,
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

describe("Test token body helper", () => {
  describe("Test working cases", () => {
    test("Get valid userId", () => {
      const rawTokenBody: RawTokenBody = {
        discordToken: "Discord",
        twitterToken: undefined,
        githubToken: undefined,
        youtubeToken: "Youtube",
        trelloToken: undefined,
        spotifyToken: undefined,
        userId: 1,
      };
      const formatedBody = BodyHelper.checkTokenBody(rawTokenBody);
      expect(formatedBody.discordToken).toBe("Discord");
      expect(formatedBody.twitterToken).toBe(undefined);
    });
  });

  describe("Test put without userId", () => {
    test("Update token with invald userId", async () => {
      const rawTokenBody: RawTokenBody = {
        discordToken: "Discord",
        twitterToken: undefined,
        githubToken: undefined,
        youtubeToken: "Youtube",
        trelloToken: undefined,
        spotifyToken: undefined,
        userId: undefined,
      };
      try {
        BodyHelper.checkTokenBody(rawTokenBody);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});
