import fs from 'fs';
import path from 'path';

import { IMAGES_DIR } from '../hobbies/hobbies';

// Helper to ensure file operations stay inside IMAGES_DIR
function safeJoin(base: string, target: string): string {
  const targetPath = path.resolve(base, target);
  if (!targetPath.startsWith(path.resolve(base))) {
    throw new Error('Invalid file path');
  }
  return targetPath;
}

export class MediaService {
  static async saveImage(filePath: string, userId: string): Promise<string> {
    try {
      const fileExtension = path.extname(filePath);
      const fileName = `${userId}-${Date.now()}${fileExtension}`;
      const newPath = safeJoin(IMAGES_DIR, fileName);

      fs.renameSync(filePath, newPath);

      return newPath.split(path.sep).join('/');
    } catch (error) {
      if (fs.existsSync(filePath)) {
        fs.unlinkSync(filePath);
      }
      throw new Error(`Failed to save profile picture: ${error}`);
    }
  }

  static async deleteImage(fileName: string): Promise<void> {
    try {
      const filePath = safeJoin(IMAGES_DIR, fileName);
      if (fs.existsSync(filePath)) {
        fs.unlinkSync(filePath);
      }
    } catch (error) {
      console.error('Failed to delete old profile picture:', error);
    }
  }

  static async deleteAllUserImages(userId: string): Promise<void> {
    try {
      if (!fs.existsSync(IMAGES_DIR)) {
        return;
      }

      const files = fs.readdirSync(IMAGES_DIR);
      const userFiles = files.filter(file => file.startsWith(userId + '-'));

      await Promise.all(userFiles.map(file => this.deleteImage(file)));
    } catch (error) {
      console.error('Failed to delete user images:', error);
    }
  }
}
