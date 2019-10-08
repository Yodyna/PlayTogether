import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fullTime'
})
export class FullTimePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    const data = new Date(value);
    return data.getHours() + ':' + (data.getMinutes() < 10 ? '0' : '') + data.getMinutes();
  }
}
