import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fullDate'
})
export class FullDatePipe implements PipeTransform {

  transform(value: Date, args?: any): any {
    return value.toLocaleDateString() + ' ' + value.toLocaleTimeString();
  }
}
