export interface User {
  id?: number;
  username: string;
  role: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  department?: string;
  position?: string;
  managerId?: number;
  managerName?: string;
}

export interface UserDetails extends User {
  notificationCount: number;
}

