import { AdvertisementService } from './services/advertisement.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AdvertisementSearchComponent } from './advertisement/advertisement-search/advertisement-search.component';
import { FirstUppercasePipe } from './shared/first-uppercase.pipe';
import { FullDatePipe } from './shared/full-date.pipe';
import { AdvertisementDetailsComponent } from './advertisement/advertisement-details/advertisement-details.component';
import { AdvertisementCreateComponent } from './advertisement/advertisement-create/advertisement-create.component';
import { LoginComponent } from './login/login.component';
import { HttpService } from './services/http.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { CommonModule } from '@angular/common';

import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { Moment } from 'moment';
import { DatePickerModule, DateTimePickerModule } from '@syncfusion/ej2-angular-calendars';

import {
  MatAutocompleteModule,
  MatBadgeModule,
  MatBottomSheetModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatTreeModule
} from '@angular/material';
import { TruncatePipe } from './shared/truncate.pipe';
import { TimeOfGameViewDirective } from './shared/time-of-game-view.directive';
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { AccountComponent } from './account/account.component';
import { AccountDialogComponent } from './account/dialog/account_dialog.component';
import { UndefinedPipe } from './shared/undefined.pipe';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AdvertisementSearchComponent,
    FirstUppercasePipe,
    FullDatePipe,
    AdvertisementDetailsComponent,
    AdvertisementCreateComponent,
    LoginComponent,
    TruncatePipe,
    TimeOfGameViewDirective,
    AccountComponent,
    AccountDialogComponent,
    UndefinedPipe
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      { path: 'create', component: AdvertisementCreateComponent },
      { path: '', component: AdvertisementSearchComponent },
      { path: 'search', component: AdvertisementSearchComponent },
      { path: 'login', component: LoginComponent },
      { path: 'detail/:id', component: AdvertisementDetailsComponent },
      { path: 'account', component: AccountComponent}
    ]),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatIconModule,
    MatInputModule,
    MatTabsModule,
    MatMomentDateModule,
    MatFormFieldModule,
    CommonModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    DatePickerModule,
    DateTimePickerModule,
    MatCheckboxModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    FontAwesomeModule
  ],
  providers: [AdvertisementService, HttpService, CookieService],
  entryComponents: [ AccountDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
