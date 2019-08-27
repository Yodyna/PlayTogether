import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { AdvertisementService } from 'src/app/services/advertisement.service';

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
  participantss = null;
  isParticiant = false;
  advertisementId;

  constructor(
    public dialogRef: MatDialogRef<EventManagementDialogComponent>,
    public dialog: MatDialog,
    public advertisementService: AdvertisementService,
    @Inject(MAT_DIALOG_DATA) public data) {
      this.switch_expression = data.name;
      this.description = this[data.description];
      if (this.switch_expression === 'participants') {
        this.participantss = data.value;
        this.advertisementId = data.advertisementId;
      }
    }

    onNoClick(): void {
      this.dialogRef.close();
    }

    openDialogMessage(participant): void {
      const dialogRef = this.dialog.open(EventManagementDialogComponent, {
        data: {name: 'message', description: 'descriptionSingleMessage'}
      });
      dialogRef.afterClosed().subscribe(result => {
        this.advertisementService.sendMessageToUser(this.advertisementId, participant.id, result).subscribe( () => {});
      });
    }

    removeEvent(participant) {
      const dialogRef = this.dialog.open(EventManagementDialogComponent, {
        data: {name: 'removeEvent', description: 'descriptionRemoveUser'}
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result === 'removeEvent') {
          this.advertisementService.kickUserFromAdvertisement(this.advertisementId, participant.id).subscribe( () => {});
        }
      });
    }
}
