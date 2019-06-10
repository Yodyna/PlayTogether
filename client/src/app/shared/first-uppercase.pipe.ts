import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'firstUppercase'
})
export class FirstUppercasePipe implements PipeTransform {

  transform(value: string, args?: any): any {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }
}
