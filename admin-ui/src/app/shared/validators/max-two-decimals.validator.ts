import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function maxTwoDecimalsValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (value === null || value === undefined || value === '') {
      return null;
    }

    // Match integer OR number with 1 or 2 decimals
    const regex = /^\d+([.,]\d{1,2})?$/;

    return regex.test(value.toString())
      ? null
      : { maxTwoDecimals: true };
  };
}
