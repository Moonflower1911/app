import {AbstractControl, ValidationErrors} from '@angular/forms';

export function maxLeadValidator(control: AbstractControl): ValidationErrors | null {
  if (!control.parent) return null;

  const minLead = control.parent.get('minLead')?.value;
  const maxLead = control.value;

  if (maxLead !== null && maxLead !== undefined && minLead !== null && minLead !== undefined) {
    if (maxLead < minLead) {
      return { leadRange: true };
    }
  }
  return null;
}
