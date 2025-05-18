import {User} from './user.model';

export interface Holiday {
  id?: number;
  name: string;
  startDate: string;
  endDate: string;
  user?: User;
  isPlanned: boolean;
  criticalityLevel: number;
  status: HolidayStatus;
  createdAt?: string;
  updatedAt?: string;
}

export enum HolidayStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED'
}

export interface HolidayRequest {
  name: string;
  startDate: string;
  endDate: string;
  isPlanned: boolean;
  criticalityLevel: number;
}
