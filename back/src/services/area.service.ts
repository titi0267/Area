import { PrismaClient, Area } from "@prisma/client";

const prisma = new PrismaClient();

const getAllArea = async (): Promise<Area[]> => {
  return prisma.area.findMany();
};

export default { getAllArea };
