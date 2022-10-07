import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";

import * as ServiceHelper from "../../src/helpers/service.helpers";

describe("Test reject invalid Area", () => {
  describe("Test working cases", () => {
    test("Reject invalid area body", async () => {
      try {
        ServiceHelper.rejectInvalidArea(12, 12, 12, 12);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test getActionFct", () => {
  describe("Test valid cases", () => {
    test("Test with invalid action service", () => {
      const action = ServiceHelper.getActionFct(12, 12);

      expect(action).toBeNull();
    });

    test("Test with invalid action", () => {
      const action = ServiceHelper.getActionFct(1, 12);

      expect(action).toBeNull();
    });
  });
});

describe("Test getReactionFct", () => {
  describe("Test valid cases", () => {
    test("Test with invalid reaction service", () => {
      const action = ServiceHelper.getReactionFct(12, 12);

      expect(action).toBeNull();
    });

    test("Test with invalid reaction", () => {
      const action = ServiceHelper.getReactionFct(1, 12);

      expect(action).toBeNull();
    });
  });
});
