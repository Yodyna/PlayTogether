import { Advertisement } from './../../models/advertisement';
import { AdvertisementService } from './../../services/advertisement.service';
import { Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy, OnChanges, DoCheck, AfterContentInit, AfterContentChecked, AfterViewChecked, AfterViewInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { faDumbbell, faCity, faRoad, faUsers, faCalendarAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-advertisement-search2',
  templateUrl: './advertisement-search2.component.html',
  styleUrls: ['./advertisement-search2.component.css']
})
export class AdvertisementSearch2Component implements OnInit, OnChanges, DoCheck, AfterContentInit, AfterContentChecked, AfterViewChecked, AfterViewInit {
  iconSport = faDumbbell;
  iconCity = faCity;
  iconSteet = faRoad;
  iconUsers = faUsers;
  iconCalendar = faCalendarAlt;
  advertisements: Array<Advertisement>;
  collorArray = ['#40407a', '#706fd3', '#34ace0', '#33d9b2', '#ff5252', '#ffb142', '#ffda79', '#a55eea', '#fd9644', '#0fb9b1', '#F97F51', '#55E6C1', '#B33771', '#25CCF7', '#0be881', '#f53b57', '#0fbcf9', '#808e9b'];
  check = false;

  constructor(private advertisementService: AdvertisementService, private httpService: HttpService, private cdref: ChangeDetectorRef) {
    this.advertisementService.getAdvertisementArray().subscribe((result: Array<Advertisement>) => {
      this.advertisements = result;
      this.advertisements.forEach (p => {
        p.color = this.getRandomColor();
      });
    });
  }

  ngOnInit() {
    this.advertisementService.getAdvertiSementObs().subscribe( (result: Array<Advertisement>) => {
      this.advertisements = result;
    });
  }

  // Uruchamia sie przy każdej zmianie
  ngDoCheck() {
    console.log('ngDoCheck');
    // this.cdref.checkNoChanges();
  }

  ngOnChanges(): void {
    console.log('ngOnChanges');
  }

  //Uruchamia sie po inicjalizacji np ng-content
  ngAfterContentInit() {
    console.log('ngAfterContentInit');
  }

  //Uruchamia sie przy kazdej zmianie
  ngAfterContentChecked() {
    console.log('ngAfterContentChecked');
    this.cdref.detectChanges();
  }

  //Uruchamai sie przy inicjalizacji widoku
  ngAfterViewInit() {
    console.log('ngAfterViewInit');
  }

  //Uruchamia sie przy kazdej zmianie widoku
  ngAfterViewChecked() {
    console.log('ngAfterViewChecked');
    this.cdref.detectChanges();
  }

  getRandomColor() {
    // const color = Math.floor(0x1000000 * Math.random()).toString(16);
    // return '4px solid' + '#' + ('000000' + color).slice(-6);
    // console.log(Math.floor((this.collorArray.length * Math.random())));
    this.check = true;
    return this.collorArray[Math.floor((this.collorArray.length * Math.random()))];
  }
}
