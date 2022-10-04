import { PrismaClient, Area } from "@prisma/client";

const prisma = new PrismaClient();

const getAllAreas = async (): Promise<Area[]> => {
  return prisma.area.findMany();
};

export default { getAllAreas };
