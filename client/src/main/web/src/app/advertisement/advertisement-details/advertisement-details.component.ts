import { Component, OnInit, Input, ChangeDetectorRef, ChangeDetectionStrategy, Inject } from '@angular/core';
import { Advertisement } from '../../models/advertisement';
import { AdvertisementService } from '../../services/advertisement.service';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { Session } from '../../models/session';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';
import { EventManagementDialogComponent } from './event-management-dialog/event-management-dialog.component';

@Component({
  selector: 'app-advertisement-details',
  templateUrl: './advertisement-details.component.html',
  styleUrls: ['./advertisement-details.component.css']
})
export class AdvertisementDetailsComponent implements OnInit {

  advertisement: Advertisement;
  session: Session;
  participant: boolean;
  params: any;
  owner = false;

  constructor(
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private httpService: HttpService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.params = params['id'];
      this.advertisementService.getAdvertisementById(params['id']).subscribe( (result: Advertisement) => {
        this.advertisement = result;
        this.getSession();
      });
    });
  }

  getSession() {
    this.httpService.getUsername().subscribe( (result: Session) => {
      this.session = result;
      result.name === this.advertisement.user.username ? this.owner = true : this.owner = false;
      if (result.authenticated) {
        this.isSubscribe();
      }
    });
  }

  isSubscribe() {
    this.advertisementService.isParticipant(this.params).subscribe( (p: boolean) => {
      this.participant = p;
    });
  }

  onNavigate() {
    window.open('//' + this.advertisement.url);
  }

  snackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 2000,
      panelClass: ['my-snack-bar']
    });
  }

  public snackbarSucces(message) {
    this.snackBar.open(message, 'close', {});
  }

  public snackbarError(message) {
    this.snackBar.open(message, 'close', {});
  }

  submit() {
    this.snackbar('Dołączyłeś do wydarzenia');
    this.advertisementService.addUserToParticipant(this.advertisement.id).subscribe(p => {
      this.isSubscribe();
      this.updateAdvertisement();
    });
  }

  delete() {
    this.snackbar('Opuściłeś wydarzenie');
    this.advertisementService.removeToAdvertisement(this.advertisement.id).subscribe(p => {
      this.isSubscribe();
      this.updateAdvertisement();
    });
  }

  updateAdvertisement() {
    this.route.params.subscribe(params => {
      this.params = params['id'];
      this.advertisementService.getAdvertisementById(params['id']).subscribe( (result: Advertisement) => {
        this.advertisement = result;
      });
    });
  }

  openDialogMessage(): void {
    const dialogRef = this.dialog.open(EventManagementDialogComponent, {
      data: {name: 'message', description: 'descriptonMultiMessage'}
    });
    dialogRef.afterClosed().subscribe(result => {
          this.advertisementService.sendMessage(+this.params, result).subscribe( () => {});
    });
  }

  getParticipants() {
    this.advertisementService.getParticipants(+this.params).subscribe( result => {
      const dialogRef = this.dialog.open(EventManagementDialogComponent, {
        data: {name: 'participants', value: result, description: 'participants', advertisementId: this.advertisement.id}
      });
      dialogRef.afterClosed().subscribe(result1 => {
      });
    });
  }

  removeEvent() {
    const dialogRef = this.dialog.open(EventManagementDialogComponent, {
      data: {name: 'removeEvent', description: 'descriptionRemoveAdvertisement'}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'removeEvent') {
        this.advertisementService.removeAdvertisement(this.advertisement.id).subscribe( () => {});
      }
    });
  }
}
