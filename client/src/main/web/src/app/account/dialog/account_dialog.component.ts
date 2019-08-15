import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-account-dialog',
  templateUrl: './account_dialog.html'
})
export class AccountDialogComponent {

  email = 'Podaj nowy email:';
  phone = 'Podaj nowy telefon:';
  gender = 'Podaj płeć:';
  birthday = 'Podaj swoją datę urodzin';
  description: string;
  isGender = false;
  category = ['Mężczyzna', 'Kobieta'];

  constructor(
    public dialogRef: MatDialogRef<AccountDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data) {
      this.description = this[data.name];
      data.name === 'gender' ? this.isGender = true : this.isGender = false;
    }

    onNoClick(): void {
      this.dialogRef.close();
    }
}
