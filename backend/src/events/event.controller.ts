
import { NextFunction, Request, Response } from 'express';

import logger from '../logger.util';
import { EVENTS } from './events';
import { GetEventResponse } from './events.types';

export class EventController {
  GetEvent(
    req: Request,
    res: Response<GetEventResponse>,
    next: NextFunction
  ) {
    try {
      res.status(200).json({
        message: 'Event fetched successfully',
        data: { events: EVENTS},
      });
    } catch (error) {
      logger.error('Failed to fetch event:', error);

      if (error instanceof Error) {
        return res.status(500).json({
          message: error.message || 'Failed to fetch events',
        });
      }

      next(error);
    }
  }
}
