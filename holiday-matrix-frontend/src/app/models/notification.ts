export interface Notification {
  id?: number;
  userId?: number;
  username?: string;
  message: string;
  isRead: boolean;
  createdAt?: Date;
}
