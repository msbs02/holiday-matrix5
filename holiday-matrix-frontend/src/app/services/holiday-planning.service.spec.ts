import { TestBed } from '@angular/core/testing';

import { HolidayPlanningService } from './holiday-planning.service';

describe('HolidayPlanningService', () => {
  let service: HolidayPlanningService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HolidayPlanningService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
