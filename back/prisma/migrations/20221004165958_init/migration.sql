-- CreateTable
CREATE TABLE `User` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(191) NOT NULL,
    `password` VARCHAR(191) NOT NULL,
    `firstName` VARCHAR(191) NOT NULL,
    `lastName` VARCHAR(191) NOT NULL,
    `role` ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',

    UNIQUE INDEX `User_email_key`(`email`),
    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `TokensTable` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `discordToken` VARCHAR(191) NULL,
    `twitterToken` VARCHAR(191) NULL,
    `githubToken` VARCHAR(191) NULL,
    `youtubeToken` VARCHAR(191) NULL,
    `trelloToken` VARCHAR(191) NULL,
    `spotifyToken` VARCHAR(191) NULL,
    `userId` INTEGER NOT NULL,

    UNIQUE INDEX `TokensTable_userId_key`(`userId`),
    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `Area` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `actionService` VARCHAR(191) NOT NULL,
    `action` VARCHAR(191) NOT NULL,
    `actionParam` VARCHAR(191) NOT NULL,
    `lastActionFetch` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `reactionService` VARCHAR(191) NOT NULL,
    `reaction` VARCHAR(191) NOT NULL,
    `reactionParam` VARCHAR(191) NOT NULL,
    `userId` INTEGER NOT NULL,

    UNIQUE INDEX `Area_userId_key`(`userId`),
    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `TokensTable` ADD CONSTRAINT `TokensTable_userId_fkey` FOREIGN KEY (`userId`) REFERENCES `User`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `Area` ADD CONSTRAINT `Area_userId_fkey` FOREIGN KEY (`userId`) REFERENCES `User`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
