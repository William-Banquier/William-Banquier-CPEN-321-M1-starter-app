import { Router } from 'express';

import { upload } from '../storage';
import { authenticateToken } from '../auth/auth.middleware';
import { MediaController } from './media.controller';

const router = Router();
const mediaController = new MediaController();

router.post(
  '/upload',
  authenticateToken,
  upload.single('file'),
  (req, res, next) => mediaController.uploadImage(req, res, next) // <-- ensure correct context
);

export default router;
