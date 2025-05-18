export interface Employee {
  id?: number;
  employeeId: string;
  name: string;
  positionId?: number;
  positionTitle?: string;
  userId?: number;
  username?: string;
  directManagerId?: number;
  directManagerName?: string;
  nextLevelManagerId?: number;
  nextLevelManagerName?: string;
}
