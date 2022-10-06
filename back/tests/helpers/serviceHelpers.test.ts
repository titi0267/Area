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
