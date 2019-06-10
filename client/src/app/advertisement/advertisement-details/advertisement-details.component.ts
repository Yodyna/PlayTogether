import { Component, OnInit, Input, ChangeDetectorRef, ChangeDetectionStrategy, Inject } from '@angular/core';
import { Advertisement } from '../../models/advertisement';
import { AdvertisementService } from '../../services/advertisement.service';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { Session } from '../../models/session';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'app-advertisement-details',
  templateUrl: './advertisement-details.component.html',
  styleUrls: ['./advertisement-details.component.css']
})
export class AdvertisementDetailsComponent implements OnInit {

  private advertisement: Advertisement;
  private session: Session;
  private participant: boolean;
  private params: any;

  constructor(
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private httpService: HttpService,
    private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.params = params['id'];
      this.advertisementService.getAdvertisementById(params['id']).subscribe( (result: Advertisement) => {
        this.advertisement = result;
      });

      this.getParticipantCount();

      this.httpService.getUsername().subscribe( (result: Session) => {
        this.session = result;

        if (result.authenticated) {
          this.isSubscribe();
        }
      });
    });
  }

  getParticipantCount() {
    this.advertisementService.getParticipantCount(this.params).subscribe( (result: number) => {
      this.advertisement.actualNumberOfParticipants = result;
    });
  }

  isSubscribe() {
    this.advertisementService.isParticipant(this.params).subscribe( (p: boolean) => {
      this.participant = p;
      console.log(p);
    });
  }

  onNavigate() {
    window.open(this.advertisement.url, '_blank');
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
      this.getParticipantCount();
    });
  }

  delete() {
    this.snackbar('Opuściłeś wydarzenie');
    this.advertisementService.removeToAdvertisement(this.advertisement.id).subscribe(p => {
      this.isSubscribe();
      this.getParticipantCount();
    });
  }
}
