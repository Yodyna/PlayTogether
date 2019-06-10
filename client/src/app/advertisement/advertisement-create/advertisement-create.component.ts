import { Component } from '@angular/core';
import { TimeOfGame } from '../../models/timeOfGame';
import { HttpService } from '../../services/http.service';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Advertisement } from '../../models/advertisement';
import { MatSnackBar } from '@angular/material';
import { AdvertisementService } from 'src/app/services/advertisement.service';

@Component({
  selector: 'app-advertisement-create',
  templateUrl: './advertisement-create.component.html',
  styleUrls: ['./advertisement-create.component.css']
})
export class AdvertisementCreateComponent {

  myControl = new FormControl();
  options: string[] = [];
  filteredOptions: Observable<string[]>;
  isHidden = false;
  title = 'Utwórz nowe wydarzenie';

  private advertisement: Advertisement = {
    sport: null,
    city: null,
    description: null,
    street: null,
    dateofCreate: new Date(),
    date: new Date(),
    timeOfGame: [],
    minNumberOfParticipants: 1,
    maxNumberOfParticipants: 99
  };
  private timeOfGame: TimeOfGame;
  private timeOfGameList: Array<TimeOfGame> = [];

  constructor(
    private httpService: HttpService,
    private snackBar: MatSnackBar,
    private advertisementService: AdvertisementService) {

    this.timeOfGame = {date: new Date()};
    this.timeOfGameList.push(this.timeOfGame);

    this.advertisementService.getAllSport().subscribe( (result: string[]) => {
      this.options = result;
      this.filter();
    });
  }

  filter() {
    this.filteredOptions = this.myControl.valueChanges
    .pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  onChange(event, i: number) {
     this.timeOfGameList[i].date = event.value;
  }

  addEvent() {
    this.timeOfGame = {date: new Date()};
    this.timeOfGameList.push(this.timeOfGame);
  }

  removeEvent(timeOfGame: TimeOfGame) {
    this.timeOfGameList = this.timeOfGameList.filter(p => p !== timeOfGame);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  snackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 2000,
      panelClass: ['my-snack-bar']
    });
  }

  submit() {
    this.advertisement.timeOfGame = this.timeOfGameList;
    this.advertisementService.addAdvertisement(this.advertisement).subscribe(
      result => {
        this.snackbar('Utworzyłeś wydarzenie');
        this.title = 'Utworzono wydarzenie pomyślnie';
        this.isHidden = true;
      },
      error => {
        this.snackbar('Nie udało się utworzyć wydarzenie, spróbuj raz jeszcze');
    });
  }
}
