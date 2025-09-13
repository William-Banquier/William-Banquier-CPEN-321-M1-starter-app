import { Router } from 'express';

import { EventController } from './event.controller';

const router = Router();
const eventController = new EventController();

router.get('/', eventController.GetEvent);

export default router;

