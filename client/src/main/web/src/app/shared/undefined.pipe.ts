import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'undefined'
})
export class UndefinedPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value !== null ? value : 'nie podano';
  }
}
