export interface HolidayPlanning {
  id?: number;
  employeeId: number;
  employeeName?: string;
  holidayPeriodId: number;
  holidayPeriodName?: string;
  status: string; // 'RED', 'YELLOW', 'GREEN'
  comment?: string;
  managerValidated: boolean;
  hosValidated: boolean;
  dgValidated: boolean;
}
