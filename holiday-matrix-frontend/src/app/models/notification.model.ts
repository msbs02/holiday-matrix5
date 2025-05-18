export interface Notification {
  id?: number;
  message: string;
  read: boolean;
  user?: any;
  createdAt?: string;
  type: NotificationType;
}

export enum NotificationType {
  HOLIDAY_REQUEST = 'HOLIDAY_REQUEST',
  VALIDATION_REMINDER = 'VALIDATION_REMINDER',
  PLANNING_CHANGE = 'PLANNING_CHANGE',
  SYSTEM = 'SYSTEM'
}
