import {AbstractControl, ValidationErrors} from '@angular/forms';

export function maxLosValidator(control: AbstractControl): ValidationErrors | null {
  if (!control.parent) return null;

  const minLos = control.parent.get('minLos')?.value;
  const maxLos = control.value;

  if (maxLos !== null && maxLos !== undefined && minLos !== null && minLos !== undefined) {
    if (maxLos < minLos) {
      return { losRange: true };
    }
  }
  return null;
}
