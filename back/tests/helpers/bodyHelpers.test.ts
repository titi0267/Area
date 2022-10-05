import { describe, test, expect } from "@jest/globals";

import {
  RawRegisterBody,
  RawLoginBody,
} from "../../src/types/body/userRequestBody.types";
import * as BodyHelper from "../../src/helpers/body.helpers";
import httpStatus from "http-status";
import { TokenService } from "../../src/services";
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
    test("Get a register body but one field undefinied", async () => {
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
});
