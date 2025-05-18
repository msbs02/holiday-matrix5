export interface MatrixEntry {
  id?: number;
  matrixId?: number;
  positionId: number;
  positionTitle?: string;
  headcount: number;
  plannedHolidayCritical?: number;
  plannedHolidayMedium?: number;
  plannedHolidayLow?: number;
  nonPlannedHolidayCritical?: number;
  nonPlannedHolidayMedium?: number;
  nonPlannedHolidayLow?: number;
}

export interface Matrix {
  id?: number;
  year: number;
  createdById?: number;
  createdByUsername?: string;
  createdAt?: Date;
  hosValidated: boolean;
  hosValidatedAt?: Date;
  hosValidatedById?: number;
  hosValidatedByUsername?: string;
  dgValidated: boolean;
  dgValidatedAt?: Date;
  dgValidatedById?: number;
  dgValidatedByUsername?: string;
  entries?: MatrixEntry[];
}
