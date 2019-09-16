import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-event-management-dialog',
  templateUrl: './event-management-dialog.component.html',
  styleUrls: ['./event-management-dialog.component.css']
})
export class EventManagementDialogComponent {

  switch_expression;
  description = '';
  descriptionSingleMessage = 'Wyślij wiadomość do użytkownika';
  descriptonMultiMessage = 'Wyślij wiadomość do wszystkich z wydarzenia';
  descriptionRemoveAdvertisement = 'Czy chcesz odwołać wydarzenie?';
  descriptionRemoveUser = 'Czy chcesz usunąć użytkownika?';
  participants = null;
  isParticiant = false;
  advertisementId;

  constructor(
    public dialogRef: MatDialogRef<EventManagementDialogComponent>,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    public advertisementService: AdvertisementService,
    @Inject(MAT_DIALOG_DATA) public data) {
      this.switch_expression = data.name;
      this.description = this[data.description];
      if (this.switch_expression === 'participants') {
        this.participants = data.value;
        this.advertisementId = data.advertisementId;
      }
    }

    snackbar(message: string) {
      this.snackBar.open(message, '', {
        duration: 2000,
        panelClass: ['my-snack-bar']
      });
    }

    onNoClick(): void {
      this.dialogRef.close();
    }

    openDialogMessage(participant): void {
      const dialogRef = this.dialog.open(EventManagementDialogComponent, {
        data: {name: 'message', description: 'descriptionSingleMessage'}
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.snackbar('Wysłano wiadomość do użytkownika');
          this.advertisementService.sendMessageToUser(this.advertisementId, participant.id, result).subscribe( () => {});
        }
      });
    }

    removeEvent(participant) {
      const dialogRef = this.dialog.open(EventManagementDialogComponent, {
        data: {name: 'removeEvent', description: 'descriptionRemoveUser'}
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result === 'removeEvent') {
          this.snackbar('Usunięto użytkownika');
          this.advertisementService.kickUserFromAdvertisement(this.advertisementId, participant.id).subscribe( () => {});
        }
      });
    }
}
