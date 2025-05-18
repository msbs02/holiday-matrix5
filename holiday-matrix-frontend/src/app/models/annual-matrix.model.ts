/*export interface AnnualMatrix {
  id?: number;
  position: string;
  hc: number;
  description: string;
  directManager: string;
  organization: string;
  nextLevelManager: string;
  employeeCount: number;
  plannedHolidayCriticality: number;
  nonPlannedHolidayCriticality: number;
  year: number;
  managerValidationStatus: ValidationStatus;
  hosValidationStatus: ValidationStatus;
  dgValidationStatus: ValidationStatus;
  manager?: any;
  createdAt?: string;
  updatedAt?: string;
}

export enum ValidationStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED'
}

export interface AnnualMatrixRequest {
  position: string;
  hc: number;
  description: string;
  directManager: string;
  organization: string;
  nextLevelManager: string;
  employeeCount: number;
  plannedHolidayCriticality: number;
  nonPlannedHolidayCriticality: number;
  year: number;
}
*/

// models/annual-matrix.model.ts
export interface AnnualMatrix {
  id?: number;
  position?: string;
  hc?: number;
  description?: string;
  directManager?: string;
  organization?: string;
  nextLevelManager?: string;
  employeeCount?: number;
  plannedHolidayCriticality?: number;
  nonPlannedHolidayCriticality?: number;
  year?: number;

  // Propriétés manquantes
  createdByUsername?: string;
  createdAt?: Date;

  hosValidated?: boolean;
  hosValidatedByUsername?: string;
  hosValidatedAt?: Date;

  dgValidated?: boolean;
  dgValidatedByUsername?: string;
  dgValidatedAt?: Date;

  entries?: MatrixEntry[];
  manager?: {
    id?: number;
    username?: string;
    // Autres propriétés du manager si nécessaire
  };
  // Autres propriétés
  managerValidationStatus?: ValidationStatus;
  hosValidationStatus?: ValidationStatus;
  dgValidationStatus?: ValidationStatus;
}

export interface MatrixEntry {
  id?: number;
  positionTitle?: string;
  headcount?: number;
  plannedHolidayCritical?: boolean | number;
  plannedHolidayMedium?: boolean | number;
  plannedHolidayLow?: boolean | number;
  nonPlannedHolidayCritical?: boolean | number;
  nonPlannedHolidayMedium?: boolean | number;
  nonPlannedHolidayLow?: boolean | number;
}

export enum ValidationStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED'
}

export interface AnnualMatrixRequest {
  position: string;
  hc: number;
  description: string;
  directManager: string;
  organization: string;
  nextLevelManager: string;
  employeeCount: number;
  plannedHolidayCriticality: number;
  nonPlannedHolidayCriticality: number;
  year: number;
}
