import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fullDate'
})
export class FullDatePipe implements PipeTransform {
  private months = ['Stycznia', 'Lutego', 'Marca', 'Kwietnia', 'Maja', 'Czerwca', 'Lipca', 'Sierpnia', 'Września', 'Października', 'Listopada', 'Grudnia'];

  transform(value: Date, args?: any): any {
    const data = new Date(value);
    return data.getDate() + ' ' + this.months[data.getUTCMonth()];
  }
}
