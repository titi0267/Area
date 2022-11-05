import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";

import * as ErrorHelper from "../../src/helpers/error.helpers";

describe("Test throwBodyError", () => {
  describe("Test working cases", () => {
    test("Throw bad request error", async () => {
      try {
        ErrorHelper.throwBodyError();
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test reject verifyIdParamIsNumber", () => {
  describe("Test working cases", () => {
    test("Get valid id param", async () => {
      const id = ErrorHelper.verifyIdParamIsNumber("12");

      expect(id).toBe(12);
    });

    test("Reject invalid id param", async () => {
      try {
        ErrorHelper.verifyIdParamIsNumber("lol");
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});
