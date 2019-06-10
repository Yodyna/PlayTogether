import { Directive, Renderer2, ElementRef, OnInit, Input } from '@angular/core';
import { TimeOfGame } from '../models/timeOfGame';

@Directive({
  selector: '[appTimeOfGameView]'
})
export class TimeOfGameViewDirective implements OnInit {

  @Input()
  private timeOfGameArray: Array<TimeOfGame>;

  private dt = new Date();
  private months = ['STY', 'LUT', 'MAR', 'KWI', 'MAJ', 'CZE', 'LIP', 'SIE', 'WRZ', 'PAZ', 'LIS', 'GRU'];
  constructor(private el: ElementRef, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.timeOfGameArray.forEach( p => {
      const li = this.el.nativeElement;
      const div = this.renderer.createElement('div');
      this.renderer.addClass(div, 'numberCircle');
      const number = this.renderer.createText(new Date(`${p.date}`).getDate().toString());
      this.renderer.appendChild(div, number);
      const div2 = this.renderer.createElement('div');
      this.renderer.addClass(div2, 'month');
      const month = this.renderer.createText(this.months[new Date(`${p.date}`).getUTCMonth()]);
      this.renderer.appendChild(div, number);
      this.renderer.appendChild(div2, month);
      this.renderer.appendChild(div, div2);
      this.renderer.appendChild(li, div);
    });
  }
}
