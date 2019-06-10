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
import { DatePickerModule, DateTimePickerModule } from '@syncfusion/ej2-angular-calendars';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatBadgeModule } from '@angular/material/badge';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSliderModule } from '@angular/material/slider';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTreeModule } from '@angular/material/tree';
import { TruncatePipe } from './shared/truncate.pipe';
import { TimeOfGameViewDirective } from './shared/time-of-game-view.directive';
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
  providers: [AdvertisementService, HttpService],
  entryComponents: [ AccountDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
