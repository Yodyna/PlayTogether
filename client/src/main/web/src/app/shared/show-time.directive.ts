import { Directive, Input, HostListener, Renderer2, ElementRef } from '@angular/core';
import { TimeOfGame } from '../models/timeOfGame';

@Directive({
  selector: '[appShowTime]'
})
export class ShowTimeDirective {

  @Input()
  private timeOfGameArray: Array<TimeOfGame>;
  private paragraph;
  private container;

  constructor(private el: ElementRef, private renderer: Renderer2) { }

  @HostListener('mouseenter')
  mouseenter() {
    this.container = this.renderer.createElement('div');
    this.renderer.addClass(this.container, 'showTime');
    const index = this.timeOfGameArray.length;

    if (index > 1) {
      const h4 = this.renderer.createElement('h4');
      h4.innerHTML = 'Terminy:';
      this.renderer.addClass(h4, 'inscription');
      this.renderer.appendChild(this.container, h4);
      this.timeOfGameArray.forEach( (p) => {
        const newDate = new Date(p.date);
        this.paragraph = this.renderer.createElement('p');
        this.paragraph.innerHTML = newDate.toLocaleString();
        this.renderer.appendChild(this.container, this.paragraph);
      });
    } else {
      const newDate = new Date(this.timeOfGameArray[0].date);
      this.paragraph = this.renderer.createElement('p');
      this.paragraph.innerHTML = newDate.toLocaleTimeString();
      this.renderer.appendChild(this.container, this.paragraph);
    }
    this.renderer.appendChild(this.el.nativeElement, this.container);
  }

  @HostListener('mouseleave')
  mouseleave() {
    this.renderer.removeChild(this.el.nativeElement, this.container);
  }

}
