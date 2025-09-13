import { EVENTS } from './events';

export type GetEventResponse = {
  message: string;
  data?: {
    events: typeof EVENTS;
  };
};

