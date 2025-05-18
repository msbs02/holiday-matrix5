import { TestBed } from '@angular/core/testing';

import { HolidayPeriodService } from './holiday-period.service';

describe('HolidayPeriodService', () => {
  let service: HolidayPeriodService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HolidayPeriodService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
